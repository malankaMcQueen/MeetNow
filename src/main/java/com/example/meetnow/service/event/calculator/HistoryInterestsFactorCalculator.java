package com.example.meetnow.service.event.calculator;

import com.example.meetnow.repository.EventRepository;
import com.example.meetnow.repository.UserActionRepository;
import com.example.meetnow.service.model.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryInterestsFactorCalculator implements FactorCalculatorStrategy {

    private final UserActionRepository actionRepository;

    private final EventRepository eventRepository;

    private static final double ZERO = 0.0;

    // todo property
    private static final double DECAY_RATE = 0.05;

    // todo починить все возможные NPE
    @Override
    public Double calculate(CalculationContext context) {
        User user = context.getUser();
        PreviewEvent event = context.getEvent();
        Set<Interest> eventInterests = event.getInterests();

        Set<UserAction> userActions = actionRepository.findAllByUserId(user.getId());

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
        Map<Long, EventDto> eventDtoMap = getEventDtoMap(userActions);

        for (UserAction action : userActions) {
            EventDto eventDto = eventDtoMap.get(action.getEventId());

            double weightDecay = calculateDecay(action.getActionTime(), context.getDateTime());
            double actionContribution = weightDecay * action.getActionType().getWeight();

            eventDto.getInterestList().forEach(interest -> {
                Double weight = interestProfile.getOrDefault(interest, ZERO);
                weight += actionContribution;
                interestProfile.put(interest, weight);
            });
        }

        return interestProfile;
    }

    private Map<Long, EventDto> getEventDtoMap(Set<UserAction> userActions) {
        return eventRepository.findAllByIds(getEventIds(userActions)).stream()
                .collect(Collectors.toMap(EventDto::getId, Function.identity()));
    }

    private double calculateDecay(LocalDateTime startTime, LocalDateTime endTime) {
        double deltaDays = ChronoUnit.HOURS.between(startTime, endTime) / 24.0;
        return Math.exp(-DECAY_RATE * deltaDays);
    }

    private static List<Long> getEventIds(Set<UserAction> userActions) {
        return userActions.stream().map(UserAction::getEventId).distinct().toList();
    }
}
