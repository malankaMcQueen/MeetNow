package com.example.meetnow.api;

import com.example.meetnow.service.model.User;
import com.example.meetnow.service.model.user.UserCreateRequest;
import com.example.meetnow.service.user.UserService;
import com.example.meetnow.service.user.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id) {
        log.info("Start /user/{}", id);
        User user = userService.getUser(id);
        log.info("End /user/{}. Response = {}", id, user);
        return user;
    }

    @PostMapping
    public User createUser(@RequestBody UserCreateRequest createRequest) {
        return userService.create(createRequest);
    }

    @PutMapping
    public User updateUser(@RequestBody UserUpdateRequest updateRequest) {
        return userService.update(updateRequest);
    }
}
