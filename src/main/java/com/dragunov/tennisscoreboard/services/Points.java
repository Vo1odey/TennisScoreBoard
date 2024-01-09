package com.dragunov.tennisscoreboard.services;

import lombok.Getter;

@Getter
public enum Points {
    ZERO(0), FIFTEEN(15), THIRTY(30), FORTY(40), GAME(777), AD("Ad");

    private final Object value;

    Points(int value) {
        this.value = value;
    }

    Points(String value) {
        this.value = value;
    }
}
