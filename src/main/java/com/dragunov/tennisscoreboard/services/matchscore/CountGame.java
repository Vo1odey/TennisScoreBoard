package com.dragunov.tennisscoreboard.services.matchscore;
import com.dragunov.tennisscoreboard.utils.MyLogger;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.logging.Level;


@Getter
@Setter
@ToString
public class CountGame {
    private CountPoint countPoint;
    private int game;
    private boolean wonSet;
    public CountGame(CountPoint countPoint){
        this.countPoint = countPoint;
        game = 0;
        wonSet = false;
    }
    MyLogger logger = MyLogger.getInstance();
    private void wonGamePoint(){
        if (countPoint.isWonGame()) {
            game++;
            countPoint.setWonGame(false);
        }
    }
    private void wonSetOngoing(MatchStatus matchStatus, PlayerMatchScore secondPlayer) {
        if (matchStatus == MatchStatus.ONGOING){
            final int DIFFERENCE = 2;
            final int NUMBER_OF_GAME_FOR_WIN = 6;
            int secondPlayerGame = secondPlayer.getCountGame().getGame();
            int currentDifference = Math.abs(game - secondPlayerGame);
            if ((currentDifference >= DIFFERENCE) && (game >= NUMBER_OF_GAME_FOR_WIN || secondPlayerGame >= NUMBER_OF_GAME_FOR_WIN)) {
                wonSet = true;
                resettingGames(secondPlayer);
            }
        }
    }
    private void wonSetTieBreak(MatchStatus matchStatus, PlayerMatchScore secondPlayer) {
        if (matchStatus == MatchStatus.TIE_BREAK){
            final int DIFFERENCE = 2;
            final int NUMBER_OF_TIE_BREAK_POINT = 7;
            int firstPlayerTieBreakPoint = this.getCountPoint().getTieBreakPoint();
            int secondPlayerTieBreakPoint = secondPlayer.getCountPoint().getTieBreakPoint();
            int currentDifference = Math.abs(firstPlayerTieBreakPoint - secondPlayerTieBreakPoint);
            if ((currentDifference >= DIFFERENCE)
                    && (firstPlayerTieBreakPoint >= NUMBER_OF_TIE_BREAK_POINT || secondPlayerTieBreakPoint >= NUMBER_OF_TIE_BREAK_POINT)) {
                wonSet = true;
                resettingGames(secondPlayer);
                resetTieBreakPoints(secondPlayer);
            }
        }
    }
    private void resetTieBreakPoints(PlayerMatchScore secondPlayer){
        this.getCountPoint().setTieBreakPoint(0);
        secondPlayer.getCountPoint().setTieBreakPoint(0);
    }
    private void resettingGames(PlayerMatchScore secondPlayer){
        game = 0;
        secondPlayer.getCountGame().setGame(0);
    }
    public void wonGame(MatchStatus matchStatus, PlayerMatchScore secondPlayer){
        wonGamePoint();
        wonSetOngoing(matchStatus, secondPlayer);
        wonSetTieBreak(matchStatus, secondPlayer);
    }
}
