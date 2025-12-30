package com.example.meetnow.service.model;

import lombok.Value;

import java.util.List;

@Value
public class User {

    Long id;

    List<Long> interestIds;

}
