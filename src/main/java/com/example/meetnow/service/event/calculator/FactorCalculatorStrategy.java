package com.example.meetnow.service.event.calculator;

import com.example.meetnow.service.model.CalculationContext;

public interface FactorCalculatorStrategy {

    Double calculate(CalculationContext context);
}
