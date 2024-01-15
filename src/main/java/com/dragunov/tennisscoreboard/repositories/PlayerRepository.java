package com.dragunov.tennisscoreboard.repositories;


import com.dragunov.tennisscoreboard.exceptions.PlayerNotFoundException;
import com.dragunov.tennisscoreboard.models.Player;
import com.dragunov.tennisscoreboard.services.matchscore.CountGame;
import com.dragunov.tennisscoreboard.services.matchscore.CountPoint;
import com.dragunov.tennisscoreboard.services.matchscore.CountSet;
import com.dragunov.tennisscoreboard.services.matchscore.PlayerMatchScore;
import com.dragunov.tennisscoreboard.utils.MyLogger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerRepository {
    private static Logger log = MyLogger.getInstance().getLogger();
    private final SessionFactory sessionFactory;

    public PlayerRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addPlayerToH2() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Bob", new PlayerMatchScore()));
        players.add(new Player("Smith", new PlayerMatchScore()));
        players.add(new Player("Kelli", new PlayerMatchScore()));
        players.add(new Player("Bred", new PlayerMatchScore()));
        players.add(new Player("Fedor", new PlayerMatchScore()));
        players.add(new Player("Maria", new PlayerMatchScore()));
        players.add(new Player("Katy", new PlayerMatchScore()));
        players.add(new Player("Helen", new PlayerMatchScore()));
        players.add(new Player("Derek", new PlayerMatchScore()));
        players.add(new Player("Kenny", new PlayerMatchScore()));
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            for (Player player:players) {
                session.persist(player);
            }
            session.getTransaction().commit();
        }
    }

    public List<Player> getPlayers() {
        List<Player> players;
        try (Session session = sessionFactory.openSession()) {
            players = session.createQuery("FROM Player", Player.class).getResultList();
            return players;
        }
    }

    public Player getPlayerByName(String name) throws PlayerNotFoundException {
        try (Session session = sessionFactory.openSession()) {
            Query<Player> query = session.createQuery("FROM Player WHERE name = :player");
            query.setParameter("player", name);
            Player player = query.uniqueResult();
            if (player == null) {
                throw new PlayerNotFoundException("Player not found");
            }
            return player;
        }
    }
    public void savePlayer(Player player){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(player);
            session.getTransaction().commit();
        }
    }
    public Player getPlayerOrCreateHim(String name) {
        Player player;
        try {
            player = getPlayerByName(name);
            player.setPlayerMatchScore(new PlayerMatchScore());
            log.log(Level.WARNING, "get player from database");
        } catch (PlayerNotFoundException e) {
            player = new Player(name, new PlayerMatchScore());
            savePlayer(player);
            log.log(Level.WARNING, "create a player and saves this in database");
        }
        return player;
    }
}

