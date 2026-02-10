package com.example.meetnow.service.model.user;

import lombok.Value;

import java.util.List;

@Value
public class UserCreateRequest {

    List<Long> interestIds;
}
