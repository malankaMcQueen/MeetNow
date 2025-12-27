package com.example.meetnow.service.event.calculator;

import com.example.meetnow.configuration.EventSortingProperties;
import com.example.meetnow.repository.EventRepository;
import com.example.meetnow.repository.UserActionRepository;
import com.example.meetnow.service.model.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.meetnow.service.model.event.RankableEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    private Map<Long, List<Interest>> getInterestByEventMap(Set<UserAction> userActions) {
        Set<Long> eventIds = userActions.stream().map(UserAction::getEventId).collect(Collectors.toSet());

        return eventRepository.findInterestsByEventIds(eventIds);
    }

    private double calculateDecay(LocalDateTime startTime, LocalDateTime endTime) {
        double deltaDays = ChronoUnit.HOURS.between(startTime, endTime) / 24.0;
        return Math.exp(-eventSortingProperties.getDecayRateForHistoryInterests() * deltaDays);
    }

}
