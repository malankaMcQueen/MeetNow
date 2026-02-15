package com.example.meetnow.service.event.calculator;

import com.example.meetnow.configuration.EventSortingProperties;
import com.example.meetnow.service.repository.EventRepository;
import com.example.meetnow.service.repository.UserActionRepository;
import com.example.meetnow.service.repository.projection.EventInterestProjection;
import com.example.meetnow.service.model.CalculationContext;
import com.example.meetnow.service.model.Interest;
import com.example.meetnow.service.model.UserAction;
import com.example.meetnow.service.model.UserContext;
import com.example.meetnow.service.model.event.RankableEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.meetnow.service.constant.Constants.ZERO;

@Service
@RequiredArgsConstructor
public class HistoryInterestsFactorCalculator implements FactorCalculatorStrategy {

    private final UserActionRepository actionRepository;

    private final EventRepository eventRepository;

    private final EventSortingProperties eventSortingProperties;

    @Override
    public Double calculate(CalculationContext context) {
        UserContext userContext = context.getUserContext();
        RankableEvent event = context.getEvent();
        Set<Interest> eventInterests = event.getInterests();

        Set<UserAction> userActions = actionRepository.findAllByUserId(userContext.getId());

        Map<Interest, Double> userInterestProfile = aggregateUserInterestWeights(userActions, context);

        return calculateEventInterestScore(eventInterests, userInterestProfile);
    }

    private static double calculateEventInterestScore(Set<Interest> eventInterests,
            Map<Interest, Double> userInterestProfile) {
        double matchedInterestsScore = eventInterests.stream()
                .mapToDouble(interest -> userInterestProfile.getOrDefault(interest, ZERO)).sum();
        double totalWeight = userInterestProfile.values().stream().mapToDouble(Double::doubleValue).sum();

        if (totalWeight == ZERO) {
            return ZERO;
        }

        return matchedInterestsScore / totalWeight;
    }

    private Map<Interest, Double> aggregateUserInterestWeights(Set<UserAction> userActions,
            CalculationContext context) {
        Map<Interest, Double> interestProfile = new HashMap<>();
        Map<Long, List<Interest>> interestByEventIdMap = getInterestByEventMap(userActions);

        for (UserAction action : userActions) {
            List<Interest> interests = interestByEventIdMap.getOrDefault(action.getEventId(), List.of());

            double weightDecay = calculateDecay(action.getActionTime(), context.getDateTime());
            double actionContribution = weightDecay * action.getActionType().getWeight();

            interests.forEach(interest -> {
                Double weight = interestProfile.getOrDefault(interest, ZERO);
                weight += actionContribution;
                interestProfile.put(interest, weight);
            });
        }

        return interestProfile;
    }

    public Map<Long, List<Interest>> getInterestByEventMap(Set<UserAction> userActions) {
        Set<Long> eventIds = extractEventIds(userActions);

        if (eventIds.isEmpty()) {
            return Map.of();
        }

        return eventRepository.findInterestsByEventIds(eventIds)
                .stream()
                .filter(projection -> projection.getEventId() != null)
                .collect(Collectors.groupingBy(
                        EventInterestProjection::getEventId,
                        Collectors.mapping(
                                projection -> mapToInterest(projection.getInterest()),
                                Collectors.filtering(
                                        Objects::nonNull,
                                        Collectors.toList()
                                )
                        )
                ));
    }

    private Interest mapToInterest(EventInterestProjection.InterestProjection projection) {
        if (projection == null) return null;
        return new Interest(projection.getId(), projection.getName(), projection.getCategoryId());
    }

    private Set<Long> extractEventIds(Set<UserAction> userActions) {
        return userActions.stream()
                .map(UserAction::getEventId) // Предположим, есть прямой метод getEventId()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private double calculateDecay(LocalDateTime startTime, LocalDateTime endTime) {
        double deltaDays = ChronoUnit.HOURS.between(startTime, endTime) / 24.0;
        return Math.exp(-eventSortingProperties.getDecayRateForHistoryInterests() * deltaDays);
    }

}
