package com.dragunov.tennisscoreboard.services;

public class Validation {

    public String validatePlayerName(String name) throws IllegalArgumentException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Player name is invalid");
        }
        String trim = name.trim();
        String firstLetter = trim.substring(0, 1).toUpperCase();
        String restOfName = trim.substring(1).toLowerCase();
        return firstLetter + restOfName;
    }
}