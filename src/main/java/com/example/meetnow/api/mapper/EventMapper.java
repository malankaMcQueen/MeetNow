package com.example.meetnow.api.mapper;

import com.example.meetnow.api.dto.CategoryDto;
import com.example.meetnow.api.dto.EventParticipantsResponse;
import com.example.meetnow.api.dto.EventResponse;
import com.example.meetnow.api.dto.InterestDto;
import com.example.meetnow.service.model.*;
import com.example.meetnow.service.model.event.Event;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class EventMapper {

    private EventMapper() {
    }

    public static EventResponse mapToEventResponse(Event event) {

        if (event == null) {
            return null;
        }

        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .startTime(event.getStartTime())
                .coordinates(new GeoPointDto(event.getCoordinates().getLatitude(), event.getCoordinates().getLongitude()))
                .participants(mapSetOfUser(event.getParticipants()))
                .organizer(mapUser(event.getOrganizer()))
                .participantsCount(event.getParticipants().size())
                .interests(mapInterests(event.getInterests()))
                .imageUrl(event.getPhoto().getPath())
                .build();
    }

    public static EventParticipantsResponse mapToParticipantsResponse(Event event) {

        if (event == null) {
            return null;
        }

        return EventParticipantsResponse.builder()
                .eventId(event.getId())
                .participants(mapSetOfUser(event.getParticipants()))
                .participantsCount(event.getParticipants().size())
                .build();
    }

    public static Set<InterestDto> mapInterests(Set<Interest> interests) {
        if (interests == null) {
            return Set.of();
        }

        return interests
                .stream()
                .map(interest -> InterestDto.builder()
                        .id(interest.getId())
                        .name(interest.getName())
                        .category(new CategoryDto(interest.getCategory().getId(), interest.getCategory().getName()))
                        .build())
                .collect(Collectors.toSet());
    }

    public static Participant mapUser(User participant) {
        if (participant == null)
            return null;
        return Participant.builder()
                        .userId(participant.getId())
                        .name(participant.getName())
                        .avatarUrl(participant.getPhoto() != null ? participant.getPhoto().getPath() : null)
                        .build();
    }

    private static Set<Participant> mapSetOfUser(Set<User> participants) {
        if (participants == null)
            return null;
        return participants.stream().map(EventMapper::mapUser
        ).collect(Collectors.toSet());
    }

    public static List<EventResponse> mapToEventResponseFromList(List<Event> events) {
        return events.stream().map(EventMapper::mapToEventResponse).toList();
    }
}
