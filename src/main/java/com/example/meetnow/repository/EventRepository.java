package com.example.meetnow.repository;

import com.example.meetnow.service.event.calculator.EventDto;
import com.example.meetnow.service.model.PreviewEvent;
import java.util.List;
import java.util.Set;

public interface EventRepository {

    List<PreviewEvent> findAll();

    Set<EventDto> findAllByIds(List<Long> eventIds);
}
