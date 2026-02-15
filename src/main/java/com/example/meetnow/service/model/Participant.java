package com.example.meetnow.service.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Participant {
    Long userId;
    String name;
    String avatar;
}
