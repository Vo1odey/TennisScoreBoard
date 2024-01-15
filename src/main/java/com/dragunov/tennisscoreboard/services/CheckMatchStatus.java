package com.dragunov.tennisscoreboard.services;

import com.dragunov.tennisscoreboard.services.matchscore.MatchStatus;
import com.dragunov.tennisscoreboard.services.matchscore.PlayerMatchScore;
import com.dragunov.tennisscoreboard.services.matchscore.Points;

import java.util.ArrayList;
import java.util.List;

import static com.dragunov.tennisscoreboard.services.matchscore.MatchStatus.END;
import static com.dragunov.tennisscoreboard.services.matchscore.MatchStatus.ONGOING;

public enum CheckMatchStatus {
    INSTANCE;
    private MatchStatus getMatchStatusForPoint(PlayerMatchScore player1, PlayerMatchScore player2){
        Points firstPlayerPoint = player1.getCountPoint().getPoint();
        Points secondPlayerPoint = player2.getCountPoint().getPoint();
        //40:40
        if (firstPlayerPoint == Points.FORTY && secondPlayerPoint == Points.FORTY) return MatchStatus.ADVANTAGE;
        //40:AD
        if (firstPlayerPoint == Points.AD || secondPlayerPoint == Points.AD) return MatchStatus.ADVANTAGE;
        return MatchStatus.ONGOING;
    }
    private MatchStatus getMatchStatusForGame(PlayerMatchScore player1, PlayerMatchScore player2) {
        final int GAME_TIE_BREAK = 6;
        int firstPlayerGame = player1.getCountGame().getGame();
        int secondPlayerGame = player2.getCountGame().getGame();
        return (firstPlayerGame == GAME_TIE_BREAK && secondPlayerGame == GAME_TIE_BREAK) ? MatchStatus.TIE_BREAK : MatchStatus.ONGOING;
    }
    private MatchStatus getMatchStatusForSet(PlayerMatchScore player1, PlayerMatchScore player2) {
        final int MATCH_END = 2;
        int firstPlayerSet = player1.getCountSet().getSet();
        int secondPlayerSet = player2.getCountSet().getSet();
        return (firstPlayerSet == MATCH_END) || (secondPlayerSet == MATCH_END) ? END : ONGOING;
    }
    public MatchStatus getMatchStatus(PlayerMatchScore player1, PlayerMatchScore player2) {
        List<MatchStatus> check = new ArrayList<>();
        MatchStatus result = MatchStatus.ONGOING;
        check.add(getMatchStatusForPoint(player1, player2));
        check.add(getMatchStatusForGame(player1, player2));
        check.add(getMatchStatusForSet(player1, player2));
        for (MatchStatus status : check) {
            result = status != MatchStatus.ONGOING ? status : result;
        }
        return result;
    }
}