package com.example.meetnow.service.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class PreviewEvent {

    Long id;
    Double weight;
    GeoPoint coordinates;
}
