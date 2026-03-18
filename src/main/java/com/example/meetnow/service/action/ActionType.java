package com.example.meetnow.service.action;

import lombok.Getter;

@Getter
public enum ActionType {
    ATTEND(1),
    SAVE(2),
    SHARE(3),
    VIEW(4),
    IGNORE(5);

    private final int id;

    ActionType(int id) {
        this.id = id;
    }
}
