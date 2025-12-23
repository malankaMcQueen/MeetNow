package com.example.meetnow.service.event;

import com.example.meetnow.service.event.calculator.FactorCalculatorStrategy;
import com.example.meetnow.service.model.CalculationContext;
import com.example.meetnow.service.model.PreviewEvent;
import com.example.meetnow.service.model.User;
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
    public List<PreviewEvent> sort(User user, List<PreviewEvent> events) {
        List<PreviewEvent> sortedEvent = events.stream()
                .map(event -> event.toBuilder().weight(calculateWeight(user, event)).build())
                .sorted(Comparator.comparing(PreviewEvent::getWeight)).toList();

        return sortedEvent;
    }

    private Double calculateWeight(User user, PreviewEvent event) {
        CalculationContext context = CalculationContext.builder().user(user).event(event).dateTime(LocalDateTime.now())
                .build();
        return factorCalculators
                .stream()
                .mapToDouble(calculator -> calculator.calculate(context))
                .sum();
    }
}
