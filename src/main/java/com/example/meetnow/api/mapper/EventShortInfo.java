package com.example.meetnow.api.mapper;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class EventShortInfo {
    private Long eventId;
    private String title;
    private String imageUrl;
    private LocalDateTime eventDate;
}