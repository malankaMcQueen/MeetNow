package com.example.meetnow.service.model;

import java.time.LocalDateTime;

import com.example.meetnow.service.model.event.RankableEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class CalculationContext {

    private UserContext userContext;

    private RankableEvent event;

    private LocalDateTime dateTime;
}
