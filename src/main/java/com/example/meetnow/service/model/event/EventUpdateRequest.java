package com.example.meetnow.service.model.event;

import com.example.meetnow.service.model.GeoPointDto;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;

@Value
public class EventUpdateRequest {

    String title;
    String description;
    LocalDateTime date;
    GeoPointDto coordinates;
    Set<Long> interestIds;
    Long imageId;

}