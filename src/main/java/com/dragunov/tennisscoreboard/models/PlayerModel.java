package com.dragunov.tennisscoreboard.models;

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
    private com.dragunov.tennisscoreboard.dto.gameScore gameScore;
    public PlayerModel() {
    }

    public PlayerModel(String name, com.dragunov.tennisscoreboard.dto.gameScore gameScore) {
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