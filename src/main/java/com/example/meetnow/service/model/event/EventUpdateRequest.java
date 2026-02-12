package com.example.meetnow.service.model.event;

import com.example.meetnow.service.model.GeoPoint;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;

@Value
public class EventUpdateRequest {

    GeoPoint coordinates;
    LocalDateTime startTime;
    Set<Long> interestIds;

}