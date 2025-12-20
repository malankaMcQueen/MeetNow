package com.example.meetnow.service.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;

@Value
@Builder(toBuilder = true)
public class PreviewEvent {

    Long id;
    Double weight;
    GeoPoint coordinates;
    LocalDateTime startTime;
    Set<Interest> interests;
}
