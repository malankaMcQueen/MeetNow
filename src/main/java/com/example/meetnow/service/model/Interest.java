package com.example.meetnow.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
public class Interest {
    private final Long id;
    private final String name;
    private final Long categoryId;
}
