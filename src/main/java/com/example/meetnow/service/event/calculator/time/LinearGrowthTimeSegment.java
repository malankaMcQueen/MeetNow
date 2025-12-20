package com.example.meetnow.service.event.calculator.time;

import org.springframework.stereotype.Service;

@Service
public class LinearGrowthTimeSegment implements TimeSegmentCalculator {

    // todo заменить на проперти
    private static final double MINIMUM_HOURS = 2.0;

    private static final double MAXIMUM_HOURS = 6.0;

    @Override
    public boolean supports(double timeBeforeEvent) {
        return MINIMUM_HOURS <= timeBeforeEvent && timeBeforeEvent <= MAXIMUM_HOURS;
    }

    @Override
    public double calculate(double timeBeforeEvent) {
        return (timeBeforeEvent - MINIMUM_HOURS) / (MAXIMUM_HOURS - MINIMUM_HOURS);
    }
}
