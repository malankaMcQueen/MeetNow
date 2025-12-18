package com.example.meetnow.service.event;

import com.example.meetnow.service.model.PreviewEvent;
import com.example.meetnow.service.model.User;

import java.util.List;

public interface EventSorter {
    List<PreviewEvent> sort(User user, List<PreviewEvent> events);
}
