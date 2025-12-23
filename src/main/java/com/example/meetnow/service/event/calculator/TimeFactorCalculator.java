package com.example.meetnow.service.event.calculator;

import static java.time.temporal.ChronoUnit.MINUTES;

import com.example.meetnow.service.event.calculator.time.TimeSegmentCalculator;
import com.example.meetnow.service.model.CalculationContext;
import com.example.meetnow.service.model.event.RankableEvent;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TimeFactorCalculator implements FactorCalculatorStrategy {

    private final List<TimeSegmentCalculator> timeSegmentCalculators;

    private static final double MINUTES_IN_HOUR = 60.0;

    @Override
    public Double calculate(CalculationContext context) {
        RankableEvent event = context.getEvent();

        double hoursToEvent = getHoursToEvent(event.getStartTime(), context.getDateTime());

        TimeSegmentCalculator timeSegmentCalculator = timeSegmentCalculators.stream()
                .filter(calculator -> calculator.supports(hoursToEvent)).findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Time TimeSegmentCalculator not found. Hours to event: " + hoursToEvent));

        return timeSegmentCalculator.calculate(hoursToEvent);
    }

    private static double getHoursToEvent(LocalDateTime eventStart, LocalDateTime now) {
        return MINUTES.between(now, eventStart) / MINUTES_IN_HOUR;
    }
}
