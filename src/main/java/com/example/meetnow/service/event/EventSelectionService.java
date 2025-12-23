package com.example.meetnow.service.event;

import com.example.meetnow.repository.EventRepository;
import com.example.meetnow.repository.UserRepository;
import com.example.meetnow.service.model.PreviewEvent;
import com.example.meetnow.service.model.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventSelectionService {

    private final EventSorter eventSorter;

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    public List<PreviewEvent> selectEventsForUser(Long userId) {
        List<PreviewEvent> events = eventRepository.findAll();
        User user = userRepository.findById(userId);

        return sortEventsByRelevance(user, events);
    }

    private List<PreviewEvent> sortEventsByRelevance(User user, List<PreviewEvent> events) {
        eventSorter.sort(user, events);
        return null;
    }
}
