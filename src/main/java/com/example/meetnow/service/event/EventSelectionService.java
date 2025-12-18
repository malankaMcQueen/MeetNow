package com.example.meetnow.service.event;

import com.example.meetnow.repository.EventRepository;
import com.example.meetnow.service.model.PreviewEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EventSelectionService {

    private final EventRepository eventRepository;

    public List<PreviewEvent> selectEventsForUser(Long userId) {
        List<PreviewEvent> events = eventRepository.findAll();
        List<PreviewEvent> sortedEvent = sortEventsByRelevance(events);
        return sortedEvent;
    }

    private List<PreviewEvent> sortEventsByRelevance(List<PreviewEvent> events) {

        return null;
    }
}
