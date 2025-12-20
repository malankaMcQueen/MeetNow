package com.example.meetnow.service.event.calculator.time;

import org.springframework.stereotype.Service;

@Service
public class OptimalTimeSegment implements TimeSegmentCalculator {

    private static final double MINIMUM_HOURS = 6.0;

    private static final double MAXIMUM_HOURS = 48.0;

    private static final double OPTIMAL_TIME_FACTOR = 1.0;

    @Override
    public boolean supports(double timeBeforeEvent) {
        return MINIMUM_HOURS <= timeBeforeEvent && timeBeforeEvent <= MAXIMUM_HOURS;
    }
    @Override
    public double calculate(double timeBeforeEvent) {
        return OPTIMAL_TIME_FACTOR;
    }
}
