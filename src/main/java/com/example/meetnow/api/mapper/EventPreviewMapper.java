package com.example.meetnow.api.mapper;

import com.example.meetnow.service.event.sorting.ScoredEvent;
import com.example.meetnow.service.model.event.Event;
import com.example.meetnow.service.model.event.EventPreviewResponse;
import com.example.meetnow.service.model.event.RankableEvent;

public final class EventPreviewMapper {

    private EventPreviewMapper() {
    }

    public static EventPreviewResponse fromEvent(Event event, double score) {
        if (event == null) {
            return null;
        }

        return EventPreviewResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .startTime(event.getStartTime())
                .coordinates(event.getCoordinates())
                .interests(EventMapper.mapInterests(event.getInterests()))
                .imageUrl(event.getPhoto() != null ? event.getPhoto().getPath() : null)
                .organizer(EventMapper.mapUser(event.getOrganizer()))
                .weight(score)
                .build();
    }

//    public static EventPreviewResponse fromRankableEvent(RankableEvent event) {
//        return EventPreviewResponse.builder().id(event.getId()).coordinates(event.getCoordinates())
//                .startTime(event.getStartTime()).interests(event.getInterests()).build();
//    }

//    public static EventPreviewResponse fromScoredEvent(ScoredEvent event) {
//        return fromRankableEvent(event.event()).toBuilder().weight(event.score()).build();
//    }
}
