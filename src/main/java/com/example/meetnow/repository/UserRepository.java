package com.example.meetnow.repository;

import com.example.meetnow.service.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    User findById(Long userId);
}
