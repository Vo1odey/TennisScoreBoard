package com.dragunov.tennisscoreboard.models;

import com.dragunov.tennisscoreboard.dto.GameScore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "PLAYERS")
public class PlayerModel {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "NAME")
    private String name;

    @Transient
    private GameScore gameScore;
    public PlayerModel() {
    }

    public PlayerModel(String name, GameScore gameScore) {
        this.name = name;
        this.gameScore = gameScore;
    }

    @Override
    public String toString() {
        return "PlayerModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}