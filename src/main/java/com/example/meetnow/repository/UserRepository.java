package com.example.meetnow.repository;

import com.example.meetnow.repository.projection.UserContextProjection;
import com.example.meetnow.service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // todo select
    Optional<UserContextProjection> findUserContextById(Long id);
}
