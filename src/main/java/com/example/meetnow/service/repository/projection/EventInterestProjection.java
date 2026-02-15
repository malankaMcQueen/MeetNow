package com.example.meetnow.service.repository.projection;

public interface EventInterestProjection {
    Long getEventId();
    InterestProjection getInterest();
    
    interface InterestProjection {
        Long getId();
        String getName();
        Long getCategoryId();
    }
}