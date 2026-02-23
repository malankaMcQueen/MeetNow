package com.example.meetnow.service.model.event;

import com.example.meetnow.api.dto.InterestDto;
import com.example.meetnow.service.model.GeoPoint;
import com.example.meetnow.service.model.Interest;
import com.example.meetnow.service.model.Participant;
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
    Set<InterestDto> interests;
    String imageUrl;
    Participant organizer;
    Double weight;

}
