package com.example.meetnow.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient chatWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8083")
                .build();
    }
}