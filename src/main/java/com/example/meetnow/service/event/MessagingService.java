package com.example.meetnow.service.event;

public interface MessagingService {

    void createChat(Long eventId, Long organizerId);

    void addParticipantInChat(Long eventId, Long newParticipantId);
}
