package com.dragunov.tennisscoreboard.repositories;

import com.dragunov.tennisscoreboard.dto.GameScore;
import com.dragunov.tennisscoreboard.models.PlayerModel;
import com.dragunov.tennisscoreboard.utils.MyLogger;
import jakarta.persistence.NoResultException;
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
        List<PlayerModel> players = new ArrayList<>();
        players.add(new PlayerModel("Bob", new GameScore()));
        players.add(new PlayerModel("Smith", new GameScore()));
        players.add(new PlayerModel("Kelli", new GameScore()));
        players.add(new PlayerModel("Bred", new GameScore()));
        players.add(new PlayerModel("Fedor", new GameScore()));
        players.add(new PlayerModel("Maria", new GameScore()));
        players.add(new PlayerModel("Katy", new GameScore()));
        players.add(new PlayerModel("Helen", new GameScore()));
        players.add(new PlayerModel("Derek", new GameScore()));
        players.add(new PlayerModel("Kenny", new GameScore()));
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            for (PlayerModel player:players) {
                session.persist(player);
            }
            session.getTransaction().commit();
        }
    }

    public List<PlayerModel> getPlayers() {
        List<PlayerModel> players;
        try (Session session = sessionFactory.openSession()) {
            players = session.createQuery("FROM PlayerModel", PlayerModel.class).getResultList();
            return players;
        }
    }

    public PlayerModel getPlayerByName(String name) throws NoResultException {
        try (Session session = sessionFactory.openSession()) {
            Query<PlayerModel> query = session.createQuery("FROM PlayerModel WHERE name = :player");
            query.setParameter("player", name);
            PlayerModel player = query.getSingleResult();
            if (player == null) {
                throw new NoResultException();
            }
            return player;
        }
    }
    public void savePlayer(PlayerModel player){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(player);
            session.getTransaction().commit();
        }
    }
    public PlayerModel getPlayerOrCreateHim(String name) {
        PlayerModel player;
        try {
            player = getPlayerByName(name);
            player.setGameScore(new GameScore());
            log.log(Level.WARNING, "get player from database - success");
        } catch (NoResultException e) {
            player = new PlayerModel(name, new GameScore());
            savePlayer(player);
            log.log(Level.WARNING, "create a player and saves this in database");
        }
        return player;
    }
}

