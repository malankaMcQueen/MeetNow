package com.example.meetnow.repository.projection;

import com.example.meetnow.service.model.Interest;

import java.util.Set;

public interface UserContextProjection {

    Long getId();

    Set<Interest> getInterests();

}
