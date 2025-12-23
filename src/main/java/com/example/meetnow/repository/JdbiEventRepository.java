package com.example.meetnow.repository;

import com.example.meetnow.service.event.calculator.EventDto;
import com.example.meetnow.service.model.event.RankableEvent;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class JdbiEventRepository implements EventRepository {

    @Override
    public List<RankableEvent> findAll() {
        return List.of();
    }

    @Override
    public Set<EventDto> findAllByIds(List<Long> eventIds) {
        return Set.of();
    }
}
