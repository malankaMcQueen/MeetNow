package com.example.meetnow.api.dto;

import com.example.meetnow.service.model.Participant;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Set;

@Value
@Builder
public class EventParticipantsResponse {

    Long eventId;
    Set<Participant> participants;
    Integer participantsCount;

}
