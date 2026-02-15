package com.example.meetnow.api.mapper;

import com.example.meetnow.api.dto.InterestDto;
import com.example.meetnow.api.dto.UserResponse;
import com.example.meetnow.service.model.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserResponse map(User user) {

        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .birthdayDate(user.getBirthdayDate())
                .description(user.getDescription())
                .imageId(user.getPhoto() != null ? user.getPhoto().getId() : null)
                .interests(mapInterests(user))
                .build();
    }

    private static Set<InterestDto> mapInterests(User user) {
        if (user.getInterests() == null) {
            return Set.of();
        }

        return user.getInterests()
                .stream()
                .map(interest -> InterestDto.builder()
                        .id(interest.getId())
                        .name(interest.getName())
                        .build())
                .collect(Collectors.toSet());
    }
}
