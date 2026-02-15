package com.example.meetnow.service.model.event;

import com.example.meetnow.service.model.GeoPoint;
import com.example.meetnow.service.model.Interest;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;

@Value
@Builder(toBuilder = true)
public class EventPreviewResponse {

    Long id;
    String title;
    String description;
    LocalDateTime startTime;
    GeoPoint coordinates;
    Set<Interest> interests;
    Double weight;

}
