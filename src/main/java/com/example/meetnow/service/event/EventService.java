package com.example.meetnow.service.event;

import com.example.meetnow.repository.EventRepository;
import com.example.meetnow.service.model.GeoPoint;
import com.example.meetnow.service.model.event.Event;
import com.example.meetnow.service.model.event.EventCreateRequest;
import com.example.meetnow.service.model.event.EventPreviewResponse;
import com.example.meetnow.service.model.event.EventUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventSelectionService eventSelectionService;

    private final EventRepository eventRepository;

    public List<EventPreviewResponse> getEventsForUser(Long userId, GeoPoint userCoordinates) {
        return eventSelectionService.getEventsForUser(userId, userCoordinates);
    }

    public Event createEvent(EventCreateRequest eventCreateRequest) {
        Event event = Event.builder()
                .coordinates(eventCreateRequest.getCoordinates())
                .startTime(eventCreateRequest.getStartTime())
                .interests(eventCreateRequest.getInterests())
                .createdTime(LocalDateTime.now())
                .build();

        return eventRepository.save(event);
    }

    public Event updateEvent(Long eventId, EventUpdateRequest eventUpdateRequest) {
        Event event = eventRepository.findById(eventId).orElseThrow(()
                -> new RuntimeException("Event with id: " + eventId + "not found" ));
        // todo Проверка прав на то может ли он менять и существует ли
        event = event.toBuilder()
                .startTime(eventUpdateRequest.getStartTime())
                .coordinates(eventUpdateRequest.getCoordinates())
                .interests(eventUpdateRequest.getInterests())
                .build();
        return eventRepository.save(event);
    }
}
