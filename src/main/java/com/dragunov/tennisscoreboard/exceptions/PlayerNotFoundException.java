package com.dragunov.tennisscoreboard.exceptions;

public class PlayerNotFoundException extends Exception{
    public PlayerNotFoundException(){
        super();
    }
    public PlayerNotFoundException(String message){
        super(message);
    }
}
