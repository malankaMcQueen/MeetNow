package com.example.meetnow.service.interest;

import com.example.meetnow.repository.InterestRepository;
import com.example.meetnow.service.model.Interest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;

    public Set<Interest> getInterestsFromIds(Set<Long> interestIds) {
        Set<Interest> interests = interestRepository.findAllByIdIn(interestIds);
        if (interests.size() != interestIds.size()) {
            throw new RuntimeException("Incorrect interest ids: " + interestIds);
        }
        return interests;
    }
}
