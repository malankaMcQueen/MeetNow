package com.example.meetnow.service.model;

import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Builder
@Value
public class User {
    Long id;
    GeoPoint coordinates;
    Set<Interest> interests;
}
