package com.example.meetnow.service.user;

import com.example.meetnow.repository.UserRepository;
import com.example.meetnow.service.model.User;
import com.example.meetnow.service.model.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserContext getUserContextById(Long userId) {
        return userRepository.getUserContextById(userId);
    }

    public User create(User user) {
        return userRepository.insert(user);
    }
}
