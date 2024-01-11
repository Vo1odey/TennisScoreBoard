package com.dragunov.tennisscoreboard.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "MATCHES")
public class Match {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @ManyToOne
    @JoinColumn(name = "Player1", referencedColumnName = "id")
    private Player Player1;


    @ManyToOne
    @JoinColumn(name = "Player2", referencedColumnName = "id")
    private Player Player2;

    @ManyToOne
    @JoinColumn(name = "Winner", referencedColumnName = "id")
    private Player Winner;

    public Match() {
    }

    public Match(Player player1, Player player2, Player winner) {
        Player1 = player1;
        Player2 = player2;
        Winner = winner;
    }

    public Match(Player player1, Player player2){
        Player1 = player1;
        Player2 = player2;
    }
}
