package com.example.meetnow.service.model;

import java.util.Set;
import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class UserContext {
    Long id;
    GeoPoint coordinates;
    Set<Interest> interests;
}
