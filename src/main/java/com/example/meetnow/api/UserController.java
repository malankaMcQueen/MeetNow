package com.example.meetnow.api;

import com.example.meetnow.api.mapper.UserMapper;
import com.example.meetnow.service.model.User;
import com.example.meetnow.service.model.user.UserCreateRequest;
import com.example.meetnow.api.dto.UserResponse;
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
    public UserResponse getUser(@PathVariable("id") Long id) {
        log.info("Start GET /user/{}", id);
        UserResponse user = UserMapper.map(userService.getUser(id));
        log.info("End GET /user/{}. Response = {}", id, user);
        return user;
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserCreateRequest createRequest) {
        log.info("Start POST /user. Request = {}", createRequest);
        UserResponse user = UserMapper.map(userService.create(createRequest));
        log.info("End POST /user. Response = {}", user);
        return user;
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateRequest updateRequest) {
        log.info("Start PUT /user. Request = {}", updateRequest);
        UserResponse user = UserMapper.map(userService.update(id, updateRequest));
        log.info("End PUT /user. Response = {}", user);
        return user;
    }
}
