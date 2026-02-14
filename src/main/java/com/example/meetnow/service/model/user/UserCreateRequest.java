package com.example.meetnow.service.model.user;

import lombok.Value;

import java.time.LocalDate;
import java.util.Set;

@Value
public class UserCreateRequest {

    String name;

    LocalDate birthdayDate;

    String description;

    Set<Long> interestIds;
}
