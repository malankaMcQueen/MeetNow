package com.example.meetnow.service.model;

import java.time.LocalDateTime;
import lombok.Value;

@Value
public class UserAction {

    Long id;

    Long userId;

    Long eventId;

    ActionType actionType;

    LocalDateTime actionTime;
}
