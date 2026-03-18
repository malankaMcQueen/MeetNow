package com.example.meetnow.service.event;

import com.example.meetnow.service.model.messaging.AddParticipantRequest;
import com.example.meetnow.service.model.messaging.CreateChatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class ChatIntegrationServiceImpl implements MessagingService {

    private final WebClient chatWebClient;

    @Override
    public void createChat(Long eventId, Long organizerId) {
     try {
         chatWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/chat/create")
                        .queryParam("eventId", eventId)
                        .queryParam("organizerId", organizerId)
                        .build())
                .retrieve()
                .toBodilessEntity()
                .block();

    } catch (WebClientResponseException ex) {
        throw new IllegalStateException(
                "ChatService returned error: " + ex.getStatusCode(), ex);
    } catch (WebClientRequestException ex) {
        throw new IllegalStateException(
                "ChatService is unavailable", ex);
    }
    }

    @Override
    public void addParticipantInChat(Long eventId, Long newParticipantId) {

        try {
            chatWebClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/chat/addParticipant")
                            .queryParam("eventId", eventId)
                            .queryParam("newParticipantId", newParticipantId)
                            .build())
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException ex) {
            throw new IllegalStateException(
                    "ChatService returned error: " + ex.getStatusCode(), ex);
        } catch (WebClientRequestException ex) {
            throw new IllegalStateException(
                    "ChatService is unavailable", ex);
        }

//        chatWebClient.post()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/chat/addParticipant")
//                        .queryParam("eventId", eventId)
//                        .queryParam("newParticipantId", newParticipantId)
//                        .build())
//                .retrieve()
//                .toBodilessEntity()
//                .block();
    }
}