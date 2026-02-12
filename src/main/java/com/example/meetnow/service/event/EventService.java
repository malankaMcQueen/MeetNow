package com.example.meetnow.service.event;

import com.example.meetnow.repository.EventRepository;
import com.example.meetnow.service.interest.InterestService;
import com.example.meetnow.service.model.GeoPoint;
import com.example.meetnow.service.model.Interest;
import com.example.meetnow.service.model.event.Event;
import com.example.meetnow.service.model.event.EventCreateRequest;
import com.example.meetnow.service.model.event.EventPreviewResponse;
import com.example.meetnow.service.model.event.EventUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final EventSelectionService eventSelectionService;

    private final InterestService interestService;

    private final EventRepository eventRepository;

    public List<EventPreviewResponse> getEventsForUser(Long userId, GeoPoint userCoordinates) {
        return eventSelectionService.getEventsForUser(userId, userCoordinates);
    }

    public Event createEvent(EventCreateRequest eventCreateRequest) {
        log.info("Create Event: {}", eventCreateRequest);
        Set<Interest> interests = Set.of();
        if (eventCreateRequest.getInterestIds() != null && !eventCreateRequest.getInterestIds().isEmpty()) {
            interests = interestService.getInterestsFromIds(eventCreateRequest.getInterestIds());
        }

        Event event = Event.builder()
                .coordinates(eventCreateRequest.getCoordinates())
                .startTime(eventCreateRequest.getStartTime())
                .interests(interests)
                .createdTime(LocalDateTime.now())
                .build();

        return eventRepository.save(event);
    }

    public Event updateEvent(Long eventId, EventUpdateRequest eventUpdateRequest) {
        Event event = eventRepository.findById(eventId).orElseThrow(()
                -> new RuntimeException("Event with id: " + eventId + "not found" ));

        // todo Проверка прав на то может ли он менять и существует ли
        Event.EventBuilder eventBuilder = event.toBuilder()
                .startTime(eventUpdateRequest.getStartTime())
                .coordinates(eventUpdateRequest.getCoordinates());

        if (eventUpdateRequest.getInterestIds() != null && !eventUpdateRequest.getInterestIds().isEmpty()) {
            Set<Interest> interests = interestService.getInterestsFromIds(eventUpdateRequest.getInterestIds());
            eventBuilder.interests(interests);
        }
        return eventRepository.save(eventBuilder.build());
    }

    public Event getEvent(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(()
                -> new RuntimeException("Event with id: " + eventId + "not found" ));
    }
}
