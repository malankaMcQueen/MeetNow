package com.example.meetnow.service.model.event;

import java.time.LocalDateTime;
import java.util.Set;

import com.example.meetnow.service.model.GeoPoint;
import com.example.meetnow.service.model.Interest;
import lombok.*;

//@Value
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@ToString
public class RankableEvent {

    private Long id;
    private GeoPoint coordinates;
    private LocalDateTime startTime;
    private Set<Interest> interests;

}
