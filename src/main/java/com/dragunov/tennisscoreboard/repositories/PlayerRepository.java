package com.dragunov.tennisscoreboard.repositories;

import com.dragunov.tennisscoreboard.dto.GameScore;
import com.dragunov.tennisscoreboard.exceptions.PlayerNotFoundException;
import com.dragunov.tennisscoreboard.models.Player;
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
        players.add(new Player("Bob", new GameScore()));
        players.add(new Player("Smith", new GameScore()));
        players.add(new Player("Kelli", new GameScore()));
        players.add(new Player("Bred", new GameScore()));
        players.add(new Player("Fedor", new GameScore()));
        players.add(new Player("Maria", new GameScore()));
        players.add(new Player("Katy", new GameScore()));
        players.add(new Player("Helen", new GameScore()));
        players.add(new Player("Derek", new GameScore()));
        players.add(new Player("Kenny", new GameScore()));
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
            player.setGameScore(new GameScore());
            log.log(Level.WARNING, "get player from database - success");
        } catch (PlayerNotFoundException e) {
            player = new Player(name, new GameScore());
            savePlayer(player);
            log.log(Level.WARNING, "create a player and saves this in database");
        }
        return player;
    }
}

