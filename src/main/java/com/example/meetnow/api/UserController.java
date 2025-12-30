package com.example.meetnow.api;

import com.example.meetnow.service.model.User;
import com.example.meetnow.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User createUser(User user) {
        return userService.create(user);
    }

    @PutMapping
    public User updateUser(User user) {
        return userService.create(user);
    }
}
