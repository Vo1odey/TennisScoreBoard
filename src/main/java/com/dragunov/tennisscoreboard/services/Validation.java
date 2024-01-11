package com.dragunov.tennisscoreboard.services;

import com.dragunov.tennisscoreboard.exceptions.InvalidPlayerNameException;

public class Validation {

    public String validatePlayerName(String name) throws InvalidPlayerNameException {
        if (name == null || name.isEmpty()) {
            throw new InvalidPlayerNameException("Player name is invalid");
        }
        String trim = name.trim();
        String firstLetter = trim.substring(0, 1).toUpperCase();
        String restOfName = trim.substring(1).toLowerCase();
        return firstLetter + restOfName;
    }
}