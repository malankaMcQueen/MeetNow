package com.example.meetnow.api;

import com.example.meetnow.service.model.User;
import com.example.meetnow.service.model.user.UserCreateRequest;
import com.example.meetnow.service.user.UserService;
import com.example.meetnow.service.user.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody UserCreateRequest createRequest) {
        return userService.create(createRequest);
    }

    @PutMapping
    public User updateUser(@RequestBody UserUpdateRequest updateRequest) {
        return userService.update(updateRequest);
    }
}
