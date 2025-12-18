package com.example.meetnow.configuration;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "control-points")
@Getter
@Setter
public class ControlPointsProperties {

    private List<ControlPoint> controlPoints;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ControlPoint {

        private double distance;

        private double value;

    }

    @PostConstruct
    void sortControlPoints() {
        controlPoints = controlPoints.stream().sorted(Comparator.comparing(ControlPoint::getDistance)).toList();
    }
}

