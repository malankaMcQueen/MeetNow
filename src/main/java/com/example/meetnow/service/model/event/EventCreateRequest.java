package com.example.meetnow.service.model.event;

import com.example.meetnow.service.model.GeoPoint;
import com.example.meetnow.service.model.GeoPointDto;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;


@Value
public class EventCreateRequest {

    String title;
    String description;
    LocalDateTime startTime;
    GeoPointDto coordinates;
    Set<Long> interestIds;
    Long organizerId;

}
