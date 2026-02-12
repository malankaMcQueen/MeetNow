package com.example.meetnow.api;

import com.example.meetnow.service.event.EventService;
import com.example.meetnow.service.model.GeoPoint;
import com.example.meetnow.service.model.event.Event;
import com.example.meetnow.service.model.event.EventCreateRequest;
import com.example.meetnow.service.model.event.EventPreviewResponse;
import com.example.meetnow.service.model.event.EventUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/recommendations/{userId}")
    public List<EventPreviewResponse> getEventListForUser(@PathVariable Long userId,
            @RequestParam Double lat,
            @RequestParam Double lon) {
        return eventService.getEventsForUser(userId, new GeoPoint(null, lat, lon));
    }

    @PostMapping
    public Event createEvent(@RequestBody EventCreateRequest eventCreateRequest) {
        return eventService.createEvent(eventCreateRequest);
    }

    @PutMapping("/{eventId}")
    public Event updateEvent(@PathVariable Long eventId, @RequestBody EventUpdateRequest eventUpdateRequest) {
        return eventService.updateEvent(eventId, eventUpdateRequest);
    }

    @GetMapping("/{eventId}")
    public Event getEvent(@PathVariable Long eventId) {
        return eventService.getEvent(eventId);
    }
}
