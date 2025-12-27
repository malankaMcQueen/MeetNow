package com.example.meetnow.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Interest {
    private final Long id;
    private final String name;
    private final Long categoryId;
}
