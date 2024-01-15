
package com.dragunov.tennisscoreboard.services.matchscore;

import lombok.Getter;

@Getter
public enum Points {
    ZERO(0), FIFTEEN(15), THIRTY(30), FORTY(40), AD("Ad");

    private final Object value;

    Points(Integer value) {
        this.value = value;
    }

    Points(String value) {
        this.value = value;
    }

    public Points next(Points point){
        Points[] values = Points.values();
        return (point.ordinal() < 3) ? values[point.ordinal()+1] : values[0]; //0, 15, 30, 40, GAME...
    }
}