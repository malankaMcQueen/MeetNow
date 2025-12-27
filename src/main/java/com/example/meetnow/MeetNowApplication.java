package com.example.meetnow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MeetNowApplication {
// tmp
    public static void main(String[] args) {
        SpringApplication.run(MeetNowApplication.class, args);
    }
}
