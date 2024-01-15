package com.dragunov.tennisscoreboard.services;

import com.dragunov.tennisscoreboard.models.Match;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {
    private final ConcurrentHashMap<UUID, Match> storage = new ConcurrentHashMap<>();

    public void recordCurrentMatch(Match match, UUID uuid) {
        storage.put(uuid, match);
    }

    public Match getMatch(UUID uuid) {
        return storage.get(uuid);
    }

    public void removeMatch(UUID uuid) {
        storage.remove(uuid);
    }
}
