package com.example.meetnow.api.mapper;

import com.example.meetnow.service.model.event.EventPreviewResponse;
import com.example.meetnow.service.model.event.RankableEvent;

public final class EventPreviewMapper {

    private EventPreviewMapper() {
    }

    public static EventPreviewResponse fromRankableEvent(RankableEvent event) {
        return EventPreviewResponse.builder().id(event.getId()).coordinates(event.getCoordinates())
                .startTime(event.getStartTime()).interests(event.getInterests()).build();
    }
}
