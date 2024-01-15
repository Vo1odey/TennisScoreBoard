package com.dragunov.tennisscoreboard.models;

import com.dragunov.tennisscoreboard.services.matchscore.PlayerMatchScore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "PLAYERS")
public class Player {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "NAME")
    private String name;
    @Transient
    private PlayerMatchScore playerMatchScore;
    public Player() {
    }
    public Player(String name, PlayerMatchScore playerMatchScore) {
        this.name = name;
        this.playerMatchScore = playerMatchScore;
    }
}