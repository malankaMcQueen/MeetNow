package com.example.meetnow.service.interest;

import com.example.meetnow.repository.InterestRepository;
import com.example.meetnow.service.model.Interest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Set<Interest> getAll() {
        return new HashSet<>(interestRepository.findAll());
    }
}
