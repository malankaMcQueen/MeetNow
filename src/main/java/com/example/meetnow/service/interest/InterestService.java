package com.example.meetnow.service.interest;

import com.example.meetnow.api.dto.EventResponse;
import com.example.meetnow.api.dto.InterestDto;
import com.example.meetnow.api.mapper.EventMapper;
import com.example.meetnow.exception.BadRequestException;
import com.example.meetnow.service.repository.InterestRepository;
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
            throw new BadRequestException("Incorrect interest ids: " + interestIds);
        }
        return interests;
    }

    public Set<InterestDto> getAll() {
        return EventMapper.mapInterests(new HashSet<>(interestRepository.findAll()));
    }
}
