package com.example.meetnow.service.user;

import com.example.meetnow.repository.UserRepository;
import com.example.meetnow.service.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(Long userId) {
        userRepository.findById(userId);
        return null;
    }
}
