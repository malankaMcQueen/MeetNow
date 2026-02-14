package com.example.meetnow.api;

import com.example.meetnow.service.interest.InterestService;
import com.example.meetnow.service.model.Interest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/interest")
@Slf4j
public class InterestController {

    private final InterestService interestService;

    @GetMapping("/all")
    public Set<Interest> getAll() {
        log.info("Start /interest/all");
        Set<Interest> interests = interestService.getAll();
        log.info("End /interest/all. Response = {}", interests);
        return interests;
    }

}
