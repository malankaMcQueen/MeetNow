package com.example.meetnow.service.model;

import java.util.Set;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class User {
    Long id;
    GeoPoint coordinates;
    Set<Interest> interests;
}
