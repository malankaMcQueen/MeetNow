package com.example.meetnow.service.user;

import lombok.Value;

import java.util.Set;

@Value
public class UserUpdateRequest {

    Long id;

    Set<Long> interestIds;
}
