package com.example.meetnow.service.model;

import lombok.Getter;

@Getter
public enum Interest {
    TENNIS(1, 10, "Tennis"), FOOTBALL(2, 10, "Football"), PAINTING(3, 20, "Painting");

    private final int id;
    private final int categoryId;
    private final String name;

    Interest(int id, int categoryId, String name) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
    }
}
