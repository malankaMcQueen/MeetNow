package com.example.meetnow.service.event.calculator;

import com.example.meetnow.configuration.ControlPointsProperties;
import com.example.meetnow.configuration.ControlPointsProperties.ControlPoint;
import com.example.meetnow.service.model.CalculationContext;
import com.example.meetnow.service.model.GeoPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistanceFactorCalculatorStrategy implements FactorCalculatorStrategy {

    private static final ControlPoint startControlPoint = new ControlPoint(0.0, 1.0);

    private final ControlPointsProperties controlPointProperties;

    @Override
    public Double calculate(CalculationContext context) {
        double distance = calculateDistance(context.getUser().getCoordinates(), context.getEvent().getCoordinates());

        ControlPoint previousControlPoint = startControlPoint;
        for (ControlPoint point : controlPointProperties.getControlPoints()) {
            if (distance <= point.getDistance()) {
                return calculateFactorValue(distance, previousControlPoint, point);
            }
            previousControlPoint = point;
        }

        return previousControlPoint.getValue();
    }

    private double calculateFactorValue(double distance,
                                        ControlPoint previousControlPoint,
                                        ControlPoint nextControlPoint) {

        double leftDistance = previousControlPoint.getDistance();
        double rightDistance = nextControlPoint.getDistance();

        double leftValue = previousControlPoint.getValue();
        double rightValue = nextControlPoint.getValue();

        double rateOfChange = (rightValue - leftValue) / (rightDistance - leftDistance);
        double distanceFromLeft = distance - leftDistance;

        double deltaValue = rateOfChange * distanceFromLeft;

        return leftValue + deltaValue;
    }

    private double calculateDistance(GeoPoint userCoordinates, GeoPoint eventCoordinates) {
        return GeoPoint.distanceKm(userCoordinates, eventCoordinates);
    }
}
