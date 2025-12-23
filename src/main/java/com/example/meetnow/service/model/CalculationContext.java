package com.example.meetnow.service.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class CalculationContext {

    private User user;

    private PreviewEvent event;

    private LocalDateTime dateTime;
}
