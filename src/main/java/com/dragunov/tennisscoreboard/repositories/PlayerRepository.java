package com.dragunov.tennisscoreboard.repositories;

import com.dragunov.tennisscoreboard.dto.GameScore;
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
        try (Session session = sessionFactory.openSession()) {
            PlayerModel player1 = new PlayerModel("Bob", new GameScore());
            PlayerModel player2 = new PlayerModel("Smith", new GameScore());
            PlayerModel player3 = new PlayerModel("Kelli", new GameScore());
            PlayerModel player4 = new PlayerModel("Bred", new GameScore());
            PlayerModel player5 = new PlayerModel("Fedor", new GameScore());
            PlayerModel player6 = new PlayerModel("Maria", new GameScore());
            PlayerModel player7 = new PlayerModel("Katy", new GameScore());
            PlayerModel player8 = new PlayerModel("Helen", new GameScore());
            PlayerModel player9 = new PlayerModel("Derek", new GameScore());
            PlayerModel player10 = new PlayerModel("Kenny", new GameScore());

            session.beginTransaction();

            session.persist(player1);
            session.persist(player2);
            session.persist(player3);
            session.persist(player4);
            session.persist(player5);
            session.persist(player6);
            session.persist(player7);
            session.persist(player8);
            session.persist(player9);
            session.persist(player10);

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
}

