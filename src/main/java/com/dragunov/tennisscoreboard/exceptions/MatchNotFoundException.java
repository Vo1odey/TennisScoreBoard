package com.dragunov.tennisscoreboard.exceptions;

public class MatchNotFoundException extends Exception {
    public MatchNotFoundException(){
        super();
    }

    public MatchNotFoundException(String message) {
        super(message);
    }
}
