package com.dragunov.tennisscoreboard.dto;

import com.dragunov.tennisscoreboard.services.Points;
import lombok.Getter;

import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class GameScore {

    private Points point;
    private int tieBreakPoint;
    private int game;
    private int set;
    private boolean advantage;
    private boolean nextPoint;

    public GameScore(){
        point = Points.ZERO;
        tieBreakPoint = 0;
        game = 0;
        set = 0;
        advantage = false;
        nextPoint = false;
    }
}

