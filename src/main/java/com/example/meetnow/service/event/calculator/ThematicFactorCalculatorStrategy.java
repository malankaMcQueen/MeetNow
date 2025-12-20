package com.example.meetnow.service.event.calculator;

import com.example.meetnow.service.model.CalculationContext;
import com.example.meetnow.service.model.Interest;
import com.example.meetnow.service.model.PreviewEvent;
import com.example.meetnow.service.model.User;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Service
public class ThematicFactorCalculatorStrategy implements FactorCalculatorStrategy {

    public static final double ZERO = 0.0;

    @Override
    public Double calculate(CalculationContext context) {
        User user = context.getUser();
        PreviewEvent event = context.getEvent();

        if (event.getInterests() == null || event.getInterests().isEmpty()) {
            return ZERO;
        }

        EnumSet<Interest> intersectionsUserAndEventInterests = EnumSet.copyOf(user.getInterests());
        // Оставляет только одинаковые элементы
        intersectionsUserAndEventInterests.retainAll(event.getInterests());

        return (double) intersectionsUserAndEventInterests.size() / event.getInterests().size();
    }
}
