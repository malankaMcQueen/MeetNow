package com.example.meetnow.service.event.calculator;

import com.example.meetnow.service.model.CalculationContext;
import com.example.meetnow.service.model.Interest;
import com.example.meetnow.service.model.event.RankableEvent;
import com.example.meetnow.service.model.User;
import java.util.EnumSet;
import org.springframework.stereotype.Service;

@Service
public class ThematicFactorCalculator implements FactorCalculatorStrategy {

    public static final double ZERO = 0.0;

    @Override
    public Double calculate(CalculationContext context) {
        User user = context.getUser();
        RankableEvent event = context.getEvent();

        if (event.getInterests() == null || event.getInterests().isEmpty()) {
            return ZERO;
        }

        EnumSet<Interest> intersectionsUserAndEventInterests = EnumSet.copyOf(user.getInterests());
        // Оставляет только одинаковые элементы
        intersectionsUserAndEventInterests.retainAll(event.getInterests());

        return (double) intersectionsUserAndEventInterests.size() / event.getInterests().size();
    }
}
