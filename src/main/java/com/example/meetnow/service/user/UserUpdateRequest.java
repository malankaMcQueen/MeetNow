package com.example.meetnow.service.user;

import lombok.Value;

import java.util.List;

@Value
public class UserUpdateRequest {

    Long id;

    List<Long> interestIds;
}
