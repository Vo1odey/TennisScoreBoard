package com.dragunov.tennisscoreboard.services;

import com.dragunov.tennisscoreboard.models.MatchModel;
import com.dragunov.tennisscoreboard.models.PlayerModel;
import com.dragunov.tennisscoreboard.repositories.MatchRepository;


import java.util.HashMap;

public class FinishedMatchesPersistenceService {

    public void saveFinishedMatch (MatchRepository matchRepository, PlayerModel player1, PlayerModel player2
            ,HashMap<String, MatchModel> storage, String uuid) {
        MatchModel matchModel;
        matchModel = storage.get(uuid);
        storage.remove(uuid);
        if (player1.getGameScore().getSet() == 2) {
            matchModel.setWinner(player1);
        } else if (player2.getGameScore().getSet() == 2) {
            matchModel.setWinner(player2);
        }
        matchRepository.mergeMatch(matchModel);
    }
}
