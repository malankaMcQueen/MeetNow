package com.example.meetnow.repository;

import com.example.meetnow.service.model.PreviewEvent;

import java.util.List;

public interface EventRepository {

    List<PreviewEvent> findAll();
}
