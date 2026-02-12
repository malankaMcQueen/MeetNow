package com.example.meetnow.service.event;

import com.example.meetnow.api.mapper.EventPreviewMapper;
import com.example.meetnow.repository.EventRepository;
import com.example.meetnow.repository.projection.RankableEventProjection;
import com.example.meetnow.service.event.sorting.EventSorter;
import com.example.meetnow.service.model.GeoPoint;
import com.example.meetnow.service.model.event.EventPreviewResponse;
import com.example.meetnow.service.model.event.RankableEvent;
import com.example.meetnow.service.model.UserContext;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

//    private List<RankableEvent> getEventListForUser() {
//        return eventRepository.findAllRankableEvents().stream().map(RankableEventProjection::toRankableEvent).toList();
//        return eventRepository.findAllRankableEvents();
//    }

    private List<RankableEvent> getEventListForUser() {
        Map<Long, RankableEvent> eventMap = new LinkedHashMap<>();

        eventRepository.findAllRankableEvents().forEach(proj -> {
            RankableEvent event = eventMap.computeIfAbsent(
                    proj.getId(),
                    id   -> RankableEvent.builder()
                            .id(proj.getId())
                            .coordinates(proj.getCoordinates())
                            .startTime(proj.getStartTime())
                            .interests(new HashSet<>())
                            .build()
            );

            // Добавляем все интересы из текущей проекции
            event.getInterests().addAll(proj.getInterests());
        });

        return eventMap.values().stream().toList();
    }

    private List<RankableEvent> sortEventsByRelevance(UserContext userContext, List<RankableEvent> events) {
        return eventSorter.sort(userContext, events);
    }
}
