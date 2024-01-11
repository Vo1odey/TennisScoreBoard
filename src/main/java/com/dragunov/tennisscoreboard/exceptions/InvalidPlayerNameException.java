package com.dragunov.tennisscoreboard.exceptions;

public class InvalidPlayerNameException extends Exception{
    public InvalidPlayerNameException() {
        super();
    }
    public InvalidPlayerNameException(String message) {
        super(message);
    }
}
