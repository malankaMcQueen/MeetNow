package com.example.meetnow.service.model;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class PreviewEvent {

    Long id;
    Double weight;
    GeoPoint coordinates;
    LocalDateTime startTime;
    Set<Interest> interests;
}
