package com.example.meetnow.service.event.sorting;

import com.example.meetnow.service.model.event.RankableEvent;
import com.example.meetnow.service.model.UserContext;
import java.util.List;

public interface EventSorter {

    List<ScoredEvent> sort(UserContext userContext, List<RankableEvent> events);

}
