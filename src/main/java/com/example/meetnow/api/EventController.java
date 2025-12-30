package com.example.meetnow.api;

import com.example.meetnow.service.event.EventService;
import com.example.meetnow.service.model.GeoPoint;
import com.example.meetnow.service.model.event.Event;
import com.example.meetnow.service.model.event.EventCreateRequest;
import com.example.meetnow.service.model.event.EventPreviewResponse;
import com.example.meetnow.service.model.event.EventUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/recommendations/{userId}")
    public List<EventPreviewResponse> getEventListForUser(@PathVariable Long userId,
            @RequestBody GeoPoint userCoordinates) {
        return eventService.getEventsForUser(userId, userCoordinates);
    }

    @PostMapping
    public Event createEvent(EventCreateRequest eventCreateRequest) {
        return eventService.createEvent(eventCreateRequest);
    }

    @PutMapping("/{eventId}")
    public Event updateEvent(@PathVariable Long eventId, EventUpdateRequest eventUpdateRequest) {
        return eventService.updateEvent(eventId, eventUpdateRequest);
    }

    @GetMapping("/{eventId}")
    public Event getEvent(@PathVariable Long eventId) {
        return null;
    }
}
