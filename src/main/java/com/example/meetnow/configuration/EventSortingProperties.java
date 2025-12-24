package com.example.meetnow.configuration;

import jakarta.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.event-sorting")
@Getter
@AllArgsConstructor
public class EventSortingProperties {

    private List<ControlPoint> distanceControlPoints;

    private double decayRateForHistoryInterests;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ControlPoint {

        private double distance;

        private double value;
    }

    @PostConstruct
    void sortControlPoints() {
        if (distanceControlPoints == null || distanceControlPoints.isEmpty()) {
            throw new IllegalArgumentException("ControlPoints is empty!");
        }
        distanceControlPoints.sort(Comparator.comparing(ControlPoint::getDistance));
    }
}
