package com.example.meetnow.service.event;

import com.example.meetnow.api.mapper.EventPreviewMapper;
import com.example.meetnow.repository.EventRepository;
import com.example.meetnow.service.event.sorting.EventSorter;
import com.example.meetnow.service.model.GeoPoint;
import com.example.meetnow.service.model.event.EventPreviewResponse;
import com.example.meetnow.service.model.event.RankableEvent;
import com.example.meetnow.service.model.UserContext;
import java.util.List;

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

        List<RankableEvent> sorted = sortEventsByRelevance(userContext, candidates);

        return sorted.stream().map(EventPreviewMapper::fromRankableEvent).toList();
    }

    private List<RankableEvent> getEventListForUser() {
        return eventRepository.findAllRankableEvents();
    }

    private List<RankableEvent> sortEventsByRelevance(UserContext userContext, List<RankableEvent> events) {
        return eventSorter.sort(userContext, events);
    }
}
