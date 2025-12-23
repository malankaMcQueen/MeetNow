package com.example.meetnow.service.event.calculator;

import com.example.meetnow.service.model.Interest;
import java.util.List;
import lombok.Value;

@Value
public class EventDto {
    Long id;

    List<Interest> interestList;
}
