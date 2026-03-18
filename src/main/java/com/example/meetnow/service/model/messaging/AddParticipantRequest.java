package com.example.meetnow.service.model.messaging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddParticipantRequest {
    private Long eventId;
    private Long participantId;
}