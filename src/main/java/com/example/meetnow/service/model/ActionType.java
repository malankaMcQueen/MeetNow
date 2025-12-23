package com.example.meetnow.service.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActionType {
    ATTENDED(1, "attended", 1.0), SAVE(3, "save", 0.6), SHARE(4, "share", 0.4), VIEW(2, "view", 0.05), IGNORE(5,
            "ignore", 0);

    private final int id;
    private final String name;
    private final double weight;
}
