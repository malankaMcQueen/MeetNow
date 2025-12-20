package com.example.meetnow.service.event.calculator;

import com.example.meetnow.service.model.CalculationContext;
import com.example.meetnow.service.event.calculator.time.TimeSegmentCalculator;
import com.example.meetnow.service.model.PreviewEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

@RequiredArgsConstructor
@Service
public class TimeFactorCalculatorStrategy implements FactorCalculatorStrategy {

    private final List<TimeSegmentCalculator> timeSegmentCalculators;

    private static final double MINUTES_IN_HOUR = 60.0;

    @Override
    public Double calculate(CalculationContext context) {
        PreviewEvent event = context.getEvent();

        double hoursToEvent = getHoursToEvent(event.getStartTime(), context.getDateTime());

        TimeSegmentCalculator timeSegmentCalculator = timeSegmentCalculators.stream()
                .filter(calculator -> calculator.supports(hoursToEvent)).findFirst().orElseThrow(()
                        -> new RuntimeException("Time TimeSegmentCalculator not found. Hours to event: " + hoursToEvent));

        return timeSegmentCalculator.calculate(hoursToEvent);


    }

    private static double getHoursToEvent(LocalDateTime eventStart, LocalDateTime now) {
        return MINUTES.between(now, eventStart) / MINUTES_IN_HOUR;
    }

}
