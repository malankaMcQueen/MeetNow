package com.example.meetnow.service.event.sorting;

import com.example.meetnow.service.event.calculator.FactorCalculatorStrategy;
import com.example.meetnow.service.model.CalculationContext;
import com.example.meetnow.service.model.event.RankableEvent;
import com.example.meetnow.service.model.UserContext;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultEventSorter implements EventSorter {

    private final List<FactorCalculatorStrategy> factorCalculators;

    @Override
    public List<RankableEvent> sort(UserContext userContext, List<RankableEvent> events) {
        CalculationContext.CalculationContextBuilder contextBuilder = CalculationContext.builder()
                .userContext(userContext).dateTime(LocalDateTime.now());

        List<ScoredEvent> scoredEvents = events.stream()
                .map(event -> new ScoredEvent(event, calculateScore(contextBuilder, event)))
                .sorted(Comparator.comparing(ScoredEvent::score).reversed()).toList();

        return scoredEvents.stream().map(ScoredEvent::event).toList();
    }

    private double calculateScore(CalculationContext.CalculationContextBuilder contextBuilder, RankableEvent event) {
        CalculationContext context = contextBuilder.event(event).build();

        return factorCalculators.stream().mapToDouble(calculator -> calculator.calculate(context)).sum();
    }
}
