package com.example.meetnow.service.event.calculator.time;

import org.springframework.stereotype.Service;

@Service
public class ExponentialDecaySegment implements TimeSegmentCalculator {

    private static final double START_HOURS = 48.0;

    private static final double DECAY_RATE = 0.05;

    @Override
    public boolean supports(double timeBeforeEvent) {
        return timeBeforeEvent > START_HOURS;
    }

    @Override
    public double calculate(double timeBeforeEvent) {
        double deltaTime = timeBeforeEvent - START_HOURS;
        return Math.exp(-DECAY_RATE * deltaTime);
    }
}
