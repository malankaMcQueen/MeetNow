package com.example.meetnow.service.event.calculator;

import com.example.meetnow.service.model.PreviewEvent;
import com.example.meetnow.service.model.User;

public interface FactorCalculatorStrategy {
    Double calculate(User user, PreviewEvent event);
}
