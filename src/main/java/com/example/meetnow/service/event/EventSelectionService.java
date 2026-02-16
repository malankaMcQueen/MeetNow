package com.example.meetnow.service.event;

import com.example.meetnow.api.mapper.EventPreviewMapper;
import com.example.meetnow.service.repository.EventRepository;
import com.example.meetnow.service.event.sorting.EventSorter;
import com.example.meetnow.service.event.sorting.ScoredEvent;
import com.example.meetnow.service.model.GeoPoint;
import com.example.meetnow.service.model.event.EventPreviewResponse;
import com.example.meetnow.service.model.event.RankableEvent;
import com.example.meetnow.service.model.UserContext;

import java.time.LocalDateTime;
import java.util.*;

import com.example.meetnow.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventSelectionService {

    private final EventSorter eventSorter;

    private final EventRepository eventRepository;

    private final UserService userService;

    public List<EventPreviewResponse> getEventsForUser(Long userId, GeoPoint userCoordinates) {
        List<RankableEvent> candidates = getEventListForUser();

        UserContext userContext = userService.getUserContextById(userId).toBuilder().coordinates(userCoordinates)
                .build();

        List<ScoredEvent> sorted = sortEventsByRelevance(userContext, candidates);

        return sorted.stream().map(EventPreviewMapper::fromScoredEvent).toList();
    }

    private List<RankableEvent> getEventListForUser() {
        List<RankableEvent> events = eventRepository.findByStartTimeAfter(LocalDateTime.now()).stream().map(proj ->
                RankableEvent.builder().id(proj.getId())
                        .startTime(proj.getStartTime())
                        .coordinates(proj.getCoordinates())
                        .interests(proj.getInterests())
                        .build())
                .toList();
        System.out.println(events);

        return events;
    }

    private List<ScoredEvent> sortEventsByRelevance(UserContext userContext, List<RankableEvent> events) {
        return eventSorter.sort(userContext, events);
    }
}
