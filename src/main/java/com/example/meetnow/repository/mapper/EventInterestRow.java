package com.example.meetnow.repository.mapper;

import com.example.meetnow.service.model.Interest;
import org.jdbi.v3.core.mapper.Nested;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record EventInterestRow(@ColumnName("event_id") Long eventId, @Nested("interest") Interest interest) {
}