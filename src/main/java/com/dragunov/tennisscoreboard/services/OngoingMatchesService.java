package com.dragunov.tennisscoreboard.services;

import com.dragunov.tennisscoreboard.models.Match;

import java.util.HashMap;

public class OngoingMatchesService {
    private HashMap<String, Match> storage = new HashMap<>();

    public void recordCurrentMatch(Match match, String uuid) {
        storage.put(uuid, match);
    }

    public Match getMatch(String uuid) {
        return storage.get(uuid);
    }

    public void removeMatch(String uuid) {
        storage.remove(uuid);
    }
}
