package com.example.meetnow.service.user;

import com.example.meetnow.service.file.FileStorageService;
import com.example.meetnow.service.model.FileResource;
import com.example.meetnow.service.repository.UserRepository;
import com.example.meetnow.service.repository.projection.UserContextProjection;
import com.example.meetnow.service.interest.InterestService;
import com.example.meetnow.service.model.Interest;
import com.example.meetnow.service.model.User;
import com.example.meetnow.service.model.UserContext;
import com.example.meetnow.service.model.user.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final InterestService interestService;
    
    private final FileStorageService fileStorageService;

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

        FileResource image = null;
        if (createRequest.getImageId() != null) {
            image = fileStorageService.getByInfoId(createRequest.getImageId());
        }

        userBuilder.name(createRequest.getName())
                .birthdayDate(createRequest.getBirthdayDate())
                .description(createRequest.getDescription())
                .photo(image);

        return userRepository.save(userBuilder.build());
    }

    public User update(Long userId, UserUpdateRequest updateRequest) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("User not found id: " + userId));

        Set<Interest> interestList = interestService.getInterestsFromIds(updateRequest.getInterestIds());

        FileResource image;
        if (updateRequest.getImageId() != null) {
            image = fileStorageService.getByInfoId(updateRequest.getImageId());
        } else {
            image = user.getPhoto();
        }

        user = user.toBuilder().name(updateRequest.getName())
                .birthdayDate(updateRequest.getBirthdayDate())
                .description(updateRequest.getDescription())
                .photo(image)
                .interests(interestList)
                .build();

        userRepository.save(user);
        return user;
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found. Id = " + userId));
    }
}
