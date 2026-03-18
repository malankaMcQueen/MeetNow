package com.example.meetnow.service.model;

import com.example.meetnow.service.action.ActionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public final class UserActionLog {

    private Long userId;

    private Long eventId;

    private ActionType actionType;

    private LocalDateTime actionTime;

}
