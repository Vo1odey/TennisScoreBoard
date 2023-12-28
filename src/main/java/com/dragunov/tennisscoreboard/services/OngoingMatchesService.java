package com.dragunov.tennisscoreboard.services;

import com.dragunov.tennisscoreboard.models.MatchModel;

import java.util.HashMap;

public class OngoingMatchesService {
    private HashMap<String, MatchModel> storage = new HashMap<>();

    public void recordCurrentMatch(MatchModel matchModel, String uuid) {
        storage.put(uuid, matchModel);
    }

    public MatchModel getMatch(String uuid) {
        return storage.get(uuid);
    }

    public void removeMatch(String uuid) {
        storage.remove(uuid);
    }
}
