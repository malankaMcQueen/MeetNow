package com.example.meetnow.repository;

import com.example.meetnow.service.model.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {

    Set<Interest> findAllByIdIn(Set<Long> ids);
}
