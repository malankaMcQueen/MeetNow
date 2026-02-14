package com.example.meetnow.service.user;

import com.example.meetnow.repository.InterestRepository;
import com.example.meetnow.repository.UserRepository;
import com.example.meetnow.repository.projection.UserContextProjection;
import com.example.meetnow.service.interest.InterestService;
import com.example.meetnow.service.model.Interest;
import com.example.meetnow.service.model.User;
import com.example.meetnow.service.model.UserContext;
import com.example.meetnow.service.model.user.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final InterestService interestService;

    public UserContext getUserContextById(Long userId) {
        UserContextProjection userContextProjection = userRepository.findUserContextById(userId).orElseThrow(()
                -> new RuntimeException("User not found id:" + userId));
        return UserContext.builder()
                .id(userContextProjection.getId())
                .interests(userContextProjection.getInterests())
                .build();
    }

    public User create(UserCreateRequest createRequest) {
        User.UserBuilder userBuilder = User.builder();

        if (createRequest.getInterestIds() != null && !createRequest.getInterestIds().isEmpty()) {
            Set<Interest> interestList = interestService.getInterestsFromIds(createRequest.getInterestIds());
            userBuilder.interests(interestList);
        }

        userBuilder.name(createRequest.getName())
                .birthdayDate(createRequest.getBirthdayDate())
                .description(createRequest.getDescription());

        return userRepository.save(userBuilder.build());
    }

    public User update(UserUpdateRequest updateRequest) {
        Set<Interest> interestList = interestService.getInterestsFromIds(updateRequest.getInterestIds());
        User user = userRepository.findById(updateRequest.getId()).orElseThrow(()
                -> new RuntimeException("User not found id: " + updateRequest.getId()));
        user.setInterests(interestList);
        userRepository.save(user);
        return user;
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found. Id = " + userId));
    }
}
