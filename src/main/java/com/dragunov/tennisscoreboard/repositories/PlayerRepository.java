package com.dragunov.tennisscoreboard.repositories;

import com.dragunov.tennisscoreboard.dto.GameScore;
import com.dragunov.tennisscoreboard.models.MatchModel;
import com.dragunov.tennisscoreboard.models.PlayerModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerRepository {
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

    public Optional<PlayerModel> getPlayer(int id){
        try (Session session = sessionFactory.openSession()) {
            PlayerModel player;
            session.beginTransaction();
            player = session.get(PlayerModel.class, id);
            session.getTransaction().commit();
            return Optional.ofNullable(player);
        }
    }

    public List<PlayerModel> getPlayers() {
        List<PlayerModel> players = new ArrayList<>();
            int id = 1;
            while (getPlayer(id).isPresent()) {
                players.add(getPlayer(id).get());
                id++;
            }
        return players;
    }

    public Optional<PlayerModel> getPlayerByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<PlayerModel> query = session.createQuery("FROM PlayerModel WHERE name = :player");
            query.setParameter("player", name);
            return Optional.ofNullable(query.uniqueResult());
        }
    }
    public void savePlayer(PlayerModel player){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(player);
            session.getTransaction().commit();
        }
    }

}

