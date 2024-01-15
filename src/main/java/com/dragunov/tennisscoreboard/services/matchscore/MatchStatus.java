package com.dragunov.tennisscoreboard.services.matchscore;

import lombok.Getter;

@Getter
public enum MatchStatus {
    ONGOING, TIE_BREAK, ADVANTAGE, END
}
