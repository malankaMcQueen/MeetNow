package com.example.meetnow.api.mapper;

import com.example.meetnow.api.dto.EventParticipantsResponse;
import com.example.meetnow.api.dto.UserResponse;
import com.example.meetnow.service.event.sorting.ScoredEvent;
import com.example.meetnow.service.model.Participant;
import com.example.meetnow.service.model.User;
import com.example.meetnow.service.model.event.Event;
import com.example.meetnow.service.model.event.EventPreviewResponse;
import com.example.meetnow.service.model.event.RankableEvent;

import java.util.Set;
import java.util.stream.Collectors;

public final class EventMapper {

    private EventMapper() {
    }

    public static EventParticipantsResponse map(Event event) {

        if (event == null) {
            return null;
        }

        return EventParticipantsResponse.builder()
                .eventId(event.getId())
                .participants(mapUser(event.getParticipants()))
                .participantsCount(event.getParticipants().size())
                .build();
    }

    private static Set<Participant> mapUser(Set<User> participants) {
        if (participants == null)
            return null;
        return participants.stream().map(participant ->
                Participant.builder()
                        .userId(participant.getId())
                        .name(participant.getName())
                        .avatar(participant.getPhoto() != null ? participant.getPhoto().getPath() : null)
                        .build()
        ).collect(Collectors.toSet());
    }
}
