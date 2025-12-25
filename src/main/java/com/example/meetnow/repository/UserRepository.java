package com.example.meetnow.repository;

import com.example.meetnow.service.model.UserContext;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    UserContext getUserContextById(Long userId);
}
