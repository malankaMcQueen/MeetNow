package com.example.meetnow.service.repository.projection;

import com.example.meetnow.service.model.Category;

public interface EventInterestProjection {
    Long getEventId();
    InterestProjection getInterest();
    
    interface InterestProjection {
        Long getId();
        String getName();
        Category getCategory();
    }
}