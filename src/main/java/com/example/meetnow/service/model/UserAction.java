package com.example.meetnow.service.model;

import lombok.Builder;
import org.jdbi.v3.core.mapper.Nested;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record UserAction(Long id, Long userId, Long eventId, @Nested("at") ActionType actionType,
        LocalDateTime actionTime) {

}
