package com.dragunov.tennisscoreboard.services.matchscore;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlayerMatchScore {
    CountPoint countPoint;
    CountGame countGame;
    CountSet countSet;
    public PlayerMatchScore(){
        this.countPoint = new CountPoint();
        this.countGame = new CountGame(countPoint);
        this.countSet = new CountSet(countGame);
    }
    public void won(MatchStatus matchStatus, PlayerMatchScore secondPlayerScore) {
        this.getCountPoint().wonPoint(matchStatus, secondPlayerScore);
        this.getCountGame().wonGame(matchStatus, secondPlayerScore);
        this.getCountSet().wonSet();
    }
}
