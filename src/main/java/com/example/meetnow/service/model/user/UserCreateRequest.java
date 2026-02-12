package com.example.meetnow.service.model.user;

import lombok.Value;

import java.util.Set;

@Value
public class UserCreateRequest {

    Set<Long> interestIds;
}
