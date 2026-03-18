package com.example.meetnow.service.event;

import com.example.meetnow.api.dto.JoinEventRequest;
import com.example.meetnow.api.mapper.EventShortInfo;
import com.example.meetnow.exception.InvalidCredentialsException;
import com.example.meetnow.exception.ResourceNotFoundException;
import com.example.meetnow.service.action.ActionService;
import com.example.meetnow.service.action.ActionType;
import com.example.meetnow.service.file.FileStorageService;
import com.example.meetnow.service.jwt.JwtService;
import com.example.meetnow.service.model.*;
import com.example.meetnow.service.repository.EventRepository;
import com.example.meetnow.service.interest.InterestService;
import com.example.meetnow.service.model.event.Event;
import com.example.meetnow.service.model.event.EventCreateRequest;
import com.example.meetnow.service.model.event.EventPreviewResponse;
import com.example.meetnow.service.model.event.EventUpdateRequest;
import com.example.meetnow.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
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

    private final ActionService actionService;

    private final MessagingService messagingService;

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
                        .address(eventCreateRequest.getCoordinates().address())
                        .latitude(eventCreateRequest.getCoordinates().latitude())
                        .longitude(eventCreateRequest.getCoordinates().longitude())
                        .build())
                .interests(interests)
                .organizer(organizer)
                .photo(image)
                .build();

        event = eventRepository.save(event);

        messagingService.createChat(event.getId(), organizer.getId());

        return event;
    }

    @Transactional
    public Event updateEvent(Long eventId, EventUpdateRequest eventUpdateRequest) {
        Event event = eventRepository.findWithAllDataById(eventId).orElseThrow(()
                -> new ResourceNotFoundException("Event with id: " + eventId + "not found"));

        Long userId = JwtService.getUserId();
        if (!event.getOrganizer().getId().equals(userId)) {
            throw new AccessDeniedException("Access denied");
        }

        FileResource image;
        if (eventUpdateRequest.getImageId() != null) {
            image = fileStorageService.getByInfoId(eventUpdateRequest.getImageId());
        } else {
            image = event.getPhoto();
        }

        if (eventUpdateRequest.getInterestIds() != null && !eventUpdateRequest.getInterestIds().isEmpty()) {
            Set<Interest> interests = interestService.getInterestsFromIds(eventUpdateRequest.getInterestIds());
            event.setInterests(interests);
        }

        event.setTitle(eventUpdateRequest.getTitle());
        event.setDescription(eventUpdateRequest.getDescription());
        event.setStartTime(eventUpdateRequest.getDate());
        event.setPhoto(image);

        return eventRepository.save(event);
    }

    public Event getEvent(Long eventId) {
        Event event = eventRepository.findWithAllDataById(eventId).orElseThrow(()
                -> new ResourceNotFoundException("Event with id: " + eventId + "not found"));

        saveUserAction(eventId, ActionType.VIEW);
        return event;
    }

    @Transactional
    public Event joinToEvent(Long eventId, JoinEventRequest request) {
//        Event event = eventRepository.findWithParticipantsById(eventId).orElseThrow(()
        Event event = eventRepository.findWithAllDataById(eventId).orElseThrow(()
                -> new ResourceNotFoundException("Event with id: " + eventId + "not found"));

        Long userId = JwtService.getUserId();
        User user = userService.getUser(userId);

        Set<User> participants = event.getParticipants();

        participants.add(user);

        event.setParticipants(participants);

        eventRepository.save(event);
        saveUserAction(eventId, com.example.meetnow.service.action.ActionType.ATTEND);

        messagingService.addParticipantInChat(eventId, userId);
        return event;
    }

    private void saveUserAction(Long eventId, com.example.meetnow.service.action.ActionType actionType) {
        Long userId = JwtService.getUserId();

        UserActionLog action = UserActionLog.builder()
                .userId(userId)
                .eventId(eventId)
                .actionTime(LocalDateTime.now())
                .actionType(actionType)
                .build();

        actionService.save(action);
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

    public void share(Long eventId) {
        saveUserAction(eventId, ActionType.SHARE);
    }

    @Transactional
    public List<EventShortInfo> getEventShortInfo(List<Long> eventIds) {
        return eventRepository.findAllById(eventIds).stream().map(event ->
                EventShortInfo.builder()
                        .title(event.getTitle())
                        .eventId(event.getId())
                        .eventDate(event.getStartTime())
                        .imageUrl(event.getPhoto() != null ? event.getPhoto().getPath() : null)
                        .build()).toList();
    }
}
