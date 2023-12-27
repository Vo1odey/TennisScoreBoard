package com.dragunov.tennisscoreboard.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "MATCHES")
public class MatchModel {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @ManyToOne
    @JoinColumn(name = "Player1", referencedColumnName = "id")
    private PlayerModel Player1;


    @ManyToOne
    @JoinColumn(name = "Player2", referencedColumnName = "id")
    private PlayerModel Player2;

    @ManyToOne
    @JoinColumn(name = "Winner", referencedColumnName = "id")
    private PlayerModel Winner;

    public MatchModel() {
    }

    public MatchModel(PlayerModel player1, PlayerModel player2, PlayerModel winner) {
        Player1 = player1;
        Player2 = player2;
        Winner = winner;
    }

    public MatchModel(PlayerModel player1, PlayerModel player2){
        Player1 = player1;
        Player2 = player2;
    }
}
