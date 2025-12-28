package com.example.meetnow.service.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ActionType {
    private final Long id;
    private final String name;
    private final double weight;
}
