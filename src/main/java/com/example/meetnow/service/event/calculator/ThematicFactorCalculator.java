package com.example.meetnow.service.event.calculator;

import com.example.meetnow.service.model.CalculationContext;
import com.example.meetnow.service.model.Interest;
import com.example.meetnow.service.model.event.RankableEvent;
import com.example.meetnow.service.model.UserContext;
import java.util.EnumSet;
import org.springframework.stereotype.Service;

import static com.example.meetnow.service.constant.Constants.ZERO;

@Service
public class ThematicFactorCalculator implements FactorCalculatorStrategy {

    @Override
    public Double calculate(CalculationContext context) {
        UserContext userContext = context.getUserContext();
        RankableEvent event = context.getEvent();

        if (event.getInterests() == null || event.getInterests().isEmpty()) {
            return ZERO;
        }

        EnumSet<Interest> intersectionsUserAndEventInterests = EnumSet.copyOf(userContext.getInterests());
        // Оставляет только одинаковые элементы
        intersectionsUserAndEventInterests.retainAll(event.getInterests());

        return (double) intersectionsUserAndEventInterests.size() / event.getInterests().size();
    }
}
