package com.example.meetnow.api.dto;

import com.example.meetnow.service.model.GeoPoint;
import com.example.meetnow.service.model.GeoPointDto;
import com.example.meetnow.service.model.Interest;
import com.example.meetnow.service.model.Participant;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;

@Value
@Builder
public class EventResponse {

    Long id;
    String title;
    String description;
    LocalDateTime startTime;
    GeoPointDto coordinates;
    Set<Participant> participants;
    Participant organizer;
    Set<InterestDto> interests;
    Integer participantsCount;
}
