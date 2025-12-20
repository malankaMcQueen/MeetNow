package com.example.meetnow.service.event.calculator.time;

public interface TimeSegmentCalculator {

    boolean supports(double timeBeforeEvent);

    double calculate(double timeBeforeEvent);
}
