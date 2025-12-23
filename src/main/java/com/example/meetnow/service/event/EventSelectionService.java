package com.example.meetnow.service.event;

import com.example.meetnow.api.mapper.EventPreviewMapper;
import com.example.meetnow.repository.EventRepository;
import com.example.meetnow.service.event.sorted.EventSorter;
import com.example.meetnow.service.model.event.EventPreviewResponse;
import com.example.meetnow.service.model.event.RankableEvent;
import com.example.meetnow.service.model.User;
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

    public List<EventPreviewResponse> getEventsForUser(Long userId) {
        List<RankableEvent> candidates = getEventListForUser(eventRepository);
        User user = userService.getUserById(userId);

        List<RankableEvent> sorted = sortEventsByRelevance(user, candidates);

        return sorted.stream().map(EventPreviewMapper::fromRankableEvent).toList();
    }

    private List<RankableEvent> getEventListForUser(EventRepository eventRepository) {
        return eventRepository.findAll();
    }

    private List<RankableEvent> sortEventsByRelevance(User user, List<RankableEvent> events) {
        return eventSorter.sort(user, events);
    }
}
