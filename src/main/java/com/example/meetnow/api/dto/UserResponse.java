package com.example.meetnow.api.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.Set;

@Value
@Builder(toBuilder = true)
public class UserResponse {

    Long id;

    String name;

    LocalDate birthdayDate;

    String description;

    Long imageId;

    Set<InterestDto> interests;
}
