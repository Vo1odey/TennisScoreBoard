package com.dragunov.tennisscoreboard.repositories;

import com.dragunov.tennisscoreboard.models.MatchModel;
import com.dragunov.tennisscoreboard.models.PlayerModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
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
    public List<MatchModel> getMatches() {
        List matches;
        try (Session session = sessionFactory.openSession()) {
            matches = session.createQuery("FROM MatchModel", MatchModel.class).getResultList();
            return matches;
        }
    }
    public void mergeMatch(MatchModel match){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(match);
            session.getTransaction().commit();
        }
    }
}
