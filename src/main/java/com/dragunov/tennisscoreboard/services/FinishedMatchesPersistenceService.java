package com.dragunov.tennisscoreboard.services;

import com.dragunov.tennisscoreboard.models.Match;
import com.dragunov.tennisscoreboard.models.Player;
import com.dragunov.tennisscoreboard.repositories.MatchRepository;

import java.util.UUID;


public class FinishedMatchesPersistenceService {

    public void saveFinishedMatch (MatchRepository matchRepository, OngoingMatchesService ongoingMatchesService, UUID uuid) {
        Match match = ongoingMatchesService.getMatch(uuid);
        ongoingMatchesService.removeMatch(uuid);
        Player player1 = match.getPlayer1();
        Player player2 = match.getPlayer2();

        if (player1.getPlayerMatchScore().getCountSet().getSet() == 2) {
            match.setWinner(player1);
        } else if (player2.getPlayerMatchScore().getCountSet().getSet() == 2) {
            match.setWinner(player2);
        }
        matchRepository.mergeMatch(match);
    }

}

