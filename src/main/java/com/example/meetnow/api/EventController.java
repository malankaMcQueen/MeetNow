package com.example.meetnow.api;

import com.example.meetnow.service.event.EventSelectionService;
import com.example.meetnow.service.model.event.EventPreviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventSelectionService eventSelectionService;

    @GetMapping
    public List<EventPreviewResponse> getEventListForUser(Long userId) {
        return eventSelectionService.getEventsForUser(userId);
    }
}
