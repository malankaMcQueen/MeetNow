package com.example.meetnow.service.event.sorted;

import com.example.meetnow.service.model.event.RankableEvent;

public record ScoredEvent(RankableEvent event, double score) {
}
