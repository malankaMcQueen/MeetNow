package com.example.meetnow.api;

import com.example.meetnow.service.event.EventService;
import com.example.meetnow.service.model.GeoPoint;
import com.example.meetnow.service.model.event.Event;
import com.example.meetnow.service.model.event.EventCreateRequest;
import com.example.meetnow.service.model.event.EventPreviewResponse;
import com.example.meetnow.service.model.event.EventUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
@Slf4j
public class EventController {

    private final EventService eventService;

    @GetMapping("/recommendations/{userId}")
    public List<EventPreviewResponse> getEventListForUser(@PathVariable Long userId,
            @RequestParam Double lat,
            @RequestParam Double lon) {
        log.info("Start GET /event/recommendations/{}. Request: lat = {}, lon = {}", userId, lat, lon);

        List<EventPreviewResponse> eventsPreviewResponse = eventService.getEventsForUser(userId, new GeoPoint(null, lat, lon));

        log.info("End GET /event/recommendations/{}. Response = {}", userId, eventsPreviewResponse);
        return eventsPreviewResponse;
    }

    @PostMapping
    public Event createEvent(@RequestBody EventCreateRequest eventCreateRequest) {
        log.info("Start POST /event. Request: {}", eventCreateRequest);
        Event event = eventService.createEvent(eventCreateRequest);
        log.info("End POST /event. Response: {}", event);
        return event;
    }

    @PutMapping("/{eventId}")
    public Event updateEvent(@PathVariable Long eventId, @RequestBody EventUpdateRequest eventUpdateRequest) {
        log.info("Start PUT /event/{} Request: {}", eventId, eventUpdateRequest);
        Event event = eventService.updateEvent(eventId, eventUpdateRequest);
        log.info("End PUT /event/{} Response: {}", eventId, event);
        return event;
    }

    @GetMapping("/{eventId}")
    public Event getEvent(@PathVariable Long eventId) {
        log.info("Start GET /event/{}", eventId);
        Event event = eventService.getEvent(eventId);
        log.info("End GET /event/{}. Response = {}", eventId, event);
        return event;
    }
}
