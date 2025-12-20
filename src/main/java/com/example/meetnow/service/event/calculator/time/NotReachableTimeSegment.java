package com.example.meetnow.service.event.calculator.time;

import org.springframework.stereotype.Service;

@Service
public class NotReachableTimeSegment implements TimeSegmentCalculator {

    private static final double MINIMUM_HOURS = 2.0;

    private static final double NOT_REACHABLE_TIME_FACTOR = 0.0;

    @Override
    public boolean supports(double timeBeforeEvent) {
        return timeBeforeEvent < MINIMUM_HOURS;
    }

    @Override
    public double calculate(double timeBeforeEvent) {
        return NOT_REACHABLE_TIME_FACTOR;
    }
}
