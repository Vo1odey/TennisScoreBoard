package com.dragunov.tennisscoreboard.services;

import com.dragunov.tennisscoreboard.models.Match;
import com.dragunov.tennisscoreboard.models.Player;
import com.dragunov.tennisscoreboard.repositories.MatchRepository;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;


public class FinishedMatchesPersistenceService {

    public void saveFinishedMatch (MatchRepository matchRepository, OngoingMatchesService ongoingMatchesService, String uuid) {
        Match match = ongoingMatchesService.getMatch(uuid);
        ongoingMatchesService.removeMatch(uuid);
        Player player1 = match.getPlayer1();
        Player player2 = match.getPlayer2();
        if (player1.getGameScore().getSet() == 2) {
            match.setWinner(player1);
        } else if (player2.getGameScore().getSet() == 2) {
            match.setWinner(player2);
        }
        matchRepository.mergeMatch(match);
    }
    public List<Match> usePaginationHibernate(SessionFactory sessionFactory, int page, String filter) {
        try (Session session = sessionFactory.openSession()){
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
    public int quantityPages(SessionFactory sessionFactory, String filter) {
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

