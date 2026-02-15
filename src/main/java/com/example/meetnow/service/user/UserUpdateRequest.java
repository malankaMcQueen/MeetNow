package com.example.meetnow.service.user;

import lombok.Value;

import java.time.LocalDate;
import java.util.Set;

@Value
public class UserUpdateRequest {

    String name;

    LocalDate birthdayDate;

    String description;

    Long imageId;

    Set<Long> interestIds;

}
