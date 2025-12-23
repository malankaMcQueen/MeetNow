package com.example.meetnow.service.event.sorted;

import com.example.meetnow.service.model.event.RankableEvent;
import com.example.meetnow.service.model.User;
import java.util.List;

public interface EventSorter {

    List<RankableEvent> sort(User user, List<RankableEvent> events);

}
