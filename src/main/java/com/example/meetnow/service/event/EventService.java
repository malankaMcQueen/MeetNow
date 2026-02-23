package com.example.meetnow.service.event;

import com.example.meetnow.api.dto.JoinEventRequest;
import com.example.meetnow.exception.InvalidCredentialsException;
import com.example.meetnow.exception.ResourceNotFoundException;
import com.example.meetnow.service.file.FileStorageService;
import com.example.meetnow.service.jwt.JwtService;
import com.example.meetnow.service.model.FileResource;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final EventSelectionService eventSelectionService;

    private final InterestService interestService;

    private final UserService userService;

    private final EventRepository eventRepository;

    private final FileStorageService fileStorageService;

    public List<EventPreviewResponse> getEventsForUser(Long userId, GeoPoint userCoordinates) {
        return eventSelectionService.getEventsForUser(userId, userCoordinates);
    }

    public Event createEvent(EventCreateRequest eventCreateRequest) {
        Set<Interest> interests = Set.of();
        if (eventCreateRequest.getInterestIds() != null && !eventCreateRequest.getInterestIds().isEmpty()) {
            interests = interestService.getInterestsFromIds(eventCreateRequest.getInterestIds());
        }

        User organizer = userService.getUser(JwtService.getUserId());

        FileResource image = null;
        if (eventCreateRequest.getImageId() != null) {
            image = fileStorageService.getByInfoId(eventCreateRequest.getImageId());
        }

        Event event = Event.builder()
                .title(eventCreateRequest.getTitle())
                .description(eventCreateRequest.getDescription())
                .createdTime(LocalDateTime.now())
                .startTime(eventCreateRequest.getStartTime())
                .active(Boolean.TRUE)
                .coordinates(GeoPoint.builder()
                        .latitude(eventCreateRequest.getCoordinates().latitude())
                        .longitude(eventCreateRequest.getCoordinates().longitude())
                        .build())
                .interests(interests)
                .organizer(organizer)
                .photo(image)
                .build();

        return eventRepository.save(event);
    }

    public Event updateEvent(Long eventId, EventUpdateRequest eventUpdateRequest) {
        Event event = eventRepository.findById(eventId).orElseThrow(()
                -> new ResourceNotFoundException("Event with id: " + eventId + "not found"));

        // todo Проверка прав на то может ли он менять и существует ли
        Event.EventBuilder eventBuilder = event.toBuilder()
                .startTime(eventUpdateRequest.getStartTime())
                .coordinates(eventUpdateRequest.getCoordinates());

        FileResource image;
        if (eventUpdateRequest.getImageId() != null) {
            image = fileStorageService.getByInfoId(eventUpdateRequest.getImageId());
        } else {
            image = event.getPhoto();
        }

        if (eventUpdateRequest.getInterestIds() != null && !eventUpdateRequest.getInterestIds().isEmpty()) {
            Set<Interest> interests = interestService.getInterestsFromIds(eventUpdateRequest.getInterestIds());
            eventBuilder.interests(interests);
        }

        eventBuilder.photo(image);

        return eventRepository.save(eventBuilder.build());
    }

    public Event getEvent(Long eventId) {
        return eventRepository.findWithAllDataById(eventId).orElseThrow(()
                -> new ResourceNotFoundException("Event with id: " + eventId + "not found"));
    }

    @Transactional
    public Event joinToEvent(Long eventId, JoinEventRequest request) {
//        Event event = eventRepository.findWithParticipantsById(eventId).orElseThrow(()
        Event event = eventRepository.findWithAllDataById(eventId).orElseThrow(()
                -> new ResourceNotFoundException("Event with id: " + eventId + "not found"));

        User user = userService.getUser(JwtService.getUserId());

        Set<User> participants = event.getParticipants();

        participants.add(user);

        event.setParticipants(participants);

        eventRepository.save(event);

        return event;
    }

    // Эвенты которые создал пользователь
    public List<Event> getCreatedEvent() {
        Long userId = JwtService.getUserId();

        return eventRepository.findByOrganizerId(userId);
    }

    public List<Event> getJoinedEvent() {
        Long userId = JwtService.getUserId();

        return eventRepository.findAllByParticipantId(userId);
    }

    public Event leaveFromEvent(Long eventId) {
//        Event event = eventRepository.findWithParticipantsById(eventId).orElseThrow(()
        Event event = eventRepository.findWithAllDataById(eventId).orElseThrow(()
                -> new ResourceNotFoundException("Event with id: " + eventId + "not found"));

        Long userId = JwtService.getUserId();
        Set<User> participants = event.getParticipants();

        participants = participants.stream()
                .filter(participant -> !participant.getId().equals(userId))
                .collect(Collectors.toSet());

        event.setParticipants(participants);

        eventRepository.save(event);

        return event;
    }

    @Transactional
    public void deactivateEvent(Long eventId) {
        Long userId = JwtService.getUserId();

        Event event = eventRepository.findById(eventId).orElseThrow(()
                -> new ResourceNotFoundException("Event with id: " + eventId + "not found"));

        if (!event.getOrganizer().getId().equals(userId)) {
            throw new InvalidCredentialsException("Unavailable operation");
        }

        event.setActive(Boolean.FALSE);
        eventRepository.save(event);
    }
}
