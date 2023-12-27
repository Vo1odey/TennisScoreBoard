package com.dragunov.tennisscoreboard.services;

import lombok.Getter;

@Getter
public enum Points {
    ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7),
    EIGHT(8), NINE(9), TEN(10), ELEVEN(11), TWELVE(12), THIRTEEN(13), FOURTEEN(14),
    FIFTEEN(15), THIRTY(30), FORTY(40), GAME(777), AD("Ad");

    private final Object value;

    Points(int value) {
        this.value = value;
    }

    Points(String value) {
        this.value = value;
    }
}
