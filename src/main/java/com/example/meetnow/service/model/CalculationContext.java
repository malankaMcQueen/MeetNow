package com.example.meetnow.service.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
public class CalculationContext {

    private User user;

    private PreviewEvent event;

    private LocalDateTime dateTime;
}

