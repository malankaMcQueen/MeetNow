package com.example.meetnow.repository;

import com.example.meetnow.service.model.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserActionRepository extends JpaRepository<UserAction, Long> {

    Set<UserAction> findAllByUserId(Long userId);
}
