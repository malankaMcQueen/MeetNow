package com.example.meetnow.service.repository.projection;

import com.example.meetnow.service.model.GeoPoint;
import com.example.meetnow.service.model.Interest;
import com.example.meetnow.service.model.event.RankableEvent;

import java.time.LocalDateTime;
import java.util.Set;

public interface RankableEventProjection {

    Long getId();
    GeoPoint getCoordinates();
    LocalDateTime getStartTime();
    Set<Interest> getInterests();

    default RankableEvent toRankableEvent() {
        return RankableEvent.builder()
                .id(getId())
                .coordinates(getCoordinates())
                .startTime(getStartTime())
                .interests(getInterests())
                .build();
    }
}
