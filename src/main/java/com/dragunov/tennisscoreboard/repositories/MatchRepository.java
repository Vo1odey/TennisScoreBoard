package com.dragunov.tennisscoreboard.repositories;

import com.dragunov.tennisscoreboard.models.Match;
import com.dragunov.tennisscoreboard.models.Player;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
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
        List<Player> players = playerRepository.getPlayers();
        Random random = new Random();
        int min = 0;
        int max = 9;
        List<Match> matches = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int p1 = random.nextInt(max - min + 1) + min;
            int p2 = random.nextInt(max - min + 1) + min;
            matches.add(new Match(players.get(p1), players.get(p2), players.get(p1)));
        }
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            for (Match match:matches) {
                session.persist(match);
            }
            session.getTransaction().commit();
        }
    }
    public List<Match> getMatches() {
        List matches;
        try (Session session = sessionFactory.openSession()) {
            matches = session.createQuery("FROM Match", Match.class).getResultList();
            return matches;
        }
    }
    public void mergeMatch(Match match){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(match);
            session.getTransaction().commit();
        }
    }

    public List<Match> usePaginationHibernate(int page, String filter) {
        try (Session session = sessionFactory.openSession()) {
            int pageSize = 5;
            int firstResult;
            if (page == 1) {
                firstResult = 0;
            } else {
                firstResult = pageSize * (page - 1);
            }
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Match> criteriaQuery = criteriaBuilder.createQuery(Match.class);
            Root<Match> root = criteriaQuery.from(Match.class);
            // Создаем предикаты для фильтрации по имени игрока
            Join<Match, Player> player1Join = root.join("Player1");
            Join<Match, Player> player2Join = root.join("Player2");
            Predicate player1NamePredicate = criteriaBuilder.like(player1Join.get("name"), "%" + filter + "%");
            Predicate player2NamePredicate = criteriaBuilder.like(player2Join.get("name"), "%" + filter + "%");
            // Создаем предикат, который объединяет предикаты по имени игрока
            Predicate playerNamePredicate = criteriaBuilder.or(player1NamePredicate, player2NamePredicate);
            // Добавляем предикат в запрос
            criteriaQuery.where(playerNamePredicate);
            CriteriaQuery<Match> selectQuery = criteriaQuery.select(root);
            TypedQuery<Match> typedQuery = session.createQuery(selectQuery);
            typedQuery.setFirstResult(firstResult);
            typedQuery.setMaxResults(pageSize);

            return typedQuery.getResultList();
        }
    }
    public int quantityPages(String filter) {
        try (Session session = sessionFactory.openSession()){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
            Root<Match> root = countQuery.from(Match.class);
            // Создаем предикаты для фильтрации по имени игрока
            Join<Match, Player> player1Join = root.join("Player1");
            Join<Match, Player> player2Join = root.join("Player2");
            Predicate player1NamePredicate = criteriaBuilder.like(player1Join.get("name"), "%" + filter + "%");
            Predicate player2NamePredicate = criteriaBuilder.like(player2Join.get("name"), "%" + filter + "%");
            // Создаем предикат, который объединяет предикаты по имени игрока
            Predicate playerNamePredicate = criteriaBuilder.or(player1NamePredicate, player2NamePredicate);
            // Добавляем предикат в запрос
            countQuery.where(playerNamePredicate);
            countQuery.select(criteriaBuilder.count(root));
            Long count = session.createQuery(countQuery).getSingleResult();
            int pageSize = 5;

            return (int) Math.ceil((double) count / pageSize);
        }
    }
}
