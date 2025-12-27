package com.example.meetnow.repository.mapper;
import com.example.meetnow.service.model.GeoPoint;
import com.example.meetnow.service.model.Interest;
import com.example.meetnow.service.model.event.RankableEvent;
import org.jdbi.v3.core.result.RowReducer;
import org.jdbi.v3.core.result.RowView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static com.example.meetnow.repository.mapper.ColumnNames.*;

public class RankableEventReducer implements RowReducer<Map<Long, RankableEvent>, RankableEvent> {

    @Override
    public Map<Long, RankableEvent> container() {
        return new HashMap<>();
    }

    @Override
    public void accumulate(Map<Long, RankableEvent> container, RowView rowView) {
        Long eventId = rowView.getColumn(EVENT_ID, Long.class);
        RankableEvent event = container.get(eventId);

        if (event == null) {
            GeoPoint coordinates = new GeoPoint(rowView.getColumn(LATITUDE, Double.class),
                    rowView.getColumn(LONGITUDE, Double.class));

            Set<Interest> interests = new HashSet<>();
            Long interestId = rowView.getColumn(INTEREST_ID, Long.class);
            if (interestId != null)
                interests.add(new Interest(interestId, "", null));

            event = RankableEvent.builder().id(eventId).coordinates(coordinates)
                    .startTime(rowView.getColumn(START_TIME, LocalDateTime.class)).interests(interests).build();

            container.put(eventId, event);
        } else {
            Long interestId = rowView.getColumn(INTEREST_ID, Long.class);
            if (interestId != null)
                event.getInterests().add(new Interest(interestId, "", null));
        }
    }

    @Override
    public Stream<RankableEvent> stream(Map<Long, RankableEvent> container) {
        return container.values().stream();
    }
}
