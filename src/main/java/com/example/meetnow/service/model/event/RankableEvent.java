package com.example.meetnow.service.model.event;

import java.time.LocalDateTime;
import java.util.Set;

import com.example.meetnow.service.model.GeoPoint;
import com.example.meetnow.service.model.Interest;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class RankableEvent {

    Long id;
    GeoPoint coordinates;
    LocalDateTime startTime;
    Set<Interest> interests;
}
