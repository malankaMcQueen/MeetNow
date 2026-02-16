package com.example.meetnow.service.event;

import com.example.meetnow.api.dto.JoinEventRequest;
import com.example.meetnow.service.repository.EventRepository;
import com.example.meetnow.service.interest.InterestService;
import com.example.meetnow.service.model.GeoPoint;
import com.example.meetnow.service.model.Interest;
import com.example.meetnow.service.model.User;
import com.example.meetnow.service.model.event.Event;
import com.example.meetnow.service.model.event.EventCreateRequest;
import com.example.meetnow.service.model.event.EventPreviewResponse;
import com.example.meetnow.service.model.event.EventUpdateRequest;
import com.example.meetnow.service.user.UserService;
import jakarta.transaction.Transactional;
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

    private final UserService userService;

    private final EventRepository eventRepository;

    public List<EventPreviewResponse> getEventsForUser(Long userId, GeoPoint userCoordinates) {
        return eventSelectionService.getEventsForUser(userId, userCoordinates);
    }

    public Event createEvent(EventCreateRequest eventCreateRequest) {
        Set<Interest> interests = Set.of();
        if (eventCreateRequest.getInterestIds() != null && !eventCreateRequest.getInterestIds().isEmpty()) {
            interests = interestService.getInterestsFromIds(eventCreateRequest.getInterestIds());
        }

        User organizer = userService.getUser(eventCreateRequest.getOrganizerId());

        Event event = Event.builder()
                .title(eventCreateRequest.getTitle())
                .description(eventCreateRequest.getDescription())
                .startTime(eventCreateRequest.getStartTime())
                .createdTime(LocalDateTime.now())
                .coordinates(GeoPoint.builder()
                        .latitude(eventCreateRequest.getCoordinates().latitude())
                        .longitude(eventCreateRequest.getCoordinates().longitude())
                        .build())
                .interests(interests)
                .organizer(organizer)
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
        return eventRepository.findWithAllDataById(eventId).orElseThrow(()
                -> new RuntimeException("Event with id: " + eventId + "not found" ));
    }

    @Transactional
    public Event joinToEvent(Long eventId, JoinEventRequest request) {
        Event event = eventRepository.findWithParticipantsById(eventId).orElseThrow(()
                -> new RuntimeException("Event with id: " + eventId + "not found" ));

        User user = userService.getUser(request.getUserId());

        Set<User> participants = event.getParticipants();

        participants.add(user);

        event.setParticipants(participants);

        eventRepository.save(event);

        return event;
    }
}
