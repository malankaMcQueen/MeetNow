package com.example.meetnow.repository;

import com.example.meetnow.service.event.calculator.EventDto;
import com.example.meetnow.service.model.event.RankableEvent;
import java.util.List;
import java.util.Set;

public interface EventRepository {

    List<RankableEvent> findAll();

    Set<EventDto> findAllByIds(List<Long> eventIds);
}
