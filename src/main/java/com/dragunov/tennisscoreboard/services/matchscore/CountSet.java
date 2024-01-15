package com.dragunov.tennisscoreboard.services.matchscore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CountSet {
    CountGame countGame;
    private boolean wonMatch;
    private int set;
    public CountSet(CountGame countGame){
        this.countGame = countGame;
        set = 0;
        wonMatch = false;
    }
    public void wonSet(){
        if (countGame.isWonSet()) {
            countGame.setWonSet(false);
            set++;
        }
    }
}
