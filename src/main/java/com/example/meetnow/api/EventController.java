package com.example.meetnow.api;

import com.example.meetnow.service.event.EventSelectionService;
import com.example.meetnow.service.model.GeoPoint;
import com.example.meetnow.service.model.event.Event;
import com.example.meetnow.service.model.event.EventPreviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventSelectionService eventSelectionService;

    @GetMapping("/recommendations/{userId}")
    public List<EventPreviewResponse> getEventListForUser(@PathVariable Long userId,
                                                          @RequestBody GeoPoint userCoordinates) {
        return eventSelectionService.getEventsForUser(userId, userCoordinates);
    }

    @PostMapping
    public Event createEvent(Event event) {
        return null;
    }

    @GetMapping("/{eventId}")
    public Event getEvent(@PathVariable Long eventId) {
        return null;
    }
}
