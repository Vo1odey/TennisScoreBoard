package com.dragunov.tennisscoreboard.repositories;

import com.dragunov.tennisscoreboard.models.MatchModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MatchRepository {
    private final SessionFactory sessionFactory;

    public MatchRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public void addMatchToTableScoreBoard(){
        PlayerRepository playerRepository = new PlayerRepository(sessionFactory);
        try (Session session = sessionFactory.openSession()){
            int id = 1;
            while (playerRepository.getPlayer(id).isPresent()
                    && playerRepository.getPlayer(++id).isPresent() ){
                MatchModel match = new MatchModel(playerRepository.getPlayer(id).get()
                        ,playerRepository.getPlayer(++id).get(), playerRepository.getPlayer(id).get());
                session.beginTransaction();

                session.persist(match);

                session.getTransaction().commit();
                id++;
                }
        }
    }
    public MatchModel getMatch(int id){
        try (Session session = sessionFactory.openSession()) {
            MatchModel match;
            session.beginTransaction();

            match = session.get(MatchModel.class, id);

            session.getTransaction().commit();
            return match;
        }
    }

}
