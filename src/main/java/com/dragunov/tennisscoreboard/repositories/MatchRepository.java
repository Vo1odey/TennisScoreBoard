package com.dragunov.tennisscoreboard.repositories;

import com.dragunov.tennisscoreboard.models.MatchModel;
import com.dragunov.tennisscoreboard.models.PlayerModel;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class MatchRepository {
    private final SessionFactory sessionFactory;

    public MatchRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public void addMatchToTableScoreBoard(){
        PlayerRepository playerRepository = new PlayerRepository(sessionFactory);
        List<PlayerModel> players = playerRepository.getPlayers();
        Random random = new Random();
        int min = 0;
        int max = 9;
        List<MatchModel> matches = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int p1 = random.nextInt(max - min + 1) + min;
            int p2 = random.nextInt(max - min + 1) + min;
            matches.add(new MatchModel(players.get(p1), players.get(p2), players.get(p1)));
        }
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            for (MatchModel match:matches) {
                session.persist(match);
            }
            session.getTransaction().commit();
        }
    }
    public Optional<MatchModel> getMatch(int id){
        try (Session session = sessionFactory.openSession()) {
            MatchModel match;
            session.beginTransaction();
            match = session.get(MatchModel.class, id);
            session.getTransaction().commit();
            return Optional.ofNullable(match);
        }
    }
    public List<MatchModel> getMatches() {
        List<MatchModel> matches = new ArrayList<>();
        int id = 1;
        while (getMatch(id).isPresent()) {
            matches.add(getMatch(id).get());
            id++;
        }
        return matches;
    }

    public void saveMatch(MatchModel match){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(match);
            session.getTransaction().commit();
        }
    }
    public void mergeMatch(MatchModel match){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(match);
            session.getTransaction().commit();
        }
    }

    public List<MatchModel> getMatchByPlayerName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<MatchModel> query = session.createQuery("FROM MatchModel WHERE Player1.name = :name OR Player2.name = :name");
            query.setParameter("name", name);
            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }
}
