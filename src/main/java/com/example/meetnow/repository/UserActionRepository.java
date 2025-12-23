package com.example.meetnow.repository;

import com.example.meetnow.service.model.UserAction;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActionRepository {

    Set<UserAction> findAllByUserId(Long id);
}
