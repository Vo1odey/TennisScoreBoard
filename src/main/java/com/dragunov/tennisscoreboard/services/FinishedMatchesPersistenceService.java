package com.dragunov.tennisscoreboard.services;

import com.dragunov.tennisscoreboard.models.MatchModel;
import com.dragunov.tennisscoreboard.models.PlayerModel;
import com.dragunov.tennisscoreboard.repositories.MatchRepository;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;


public class FinishedMatchesPersistenceService {

    public void saveFinishedMatch (MatchRepository matchRepository, OngoingMatchesService ongoingMatchesService, String uuid) {
        MatchModel matchModel = ongoingMatchesService.getMatch(uuid);
        ongoingMatchesService.removeMatch(uuid);
        PlayerModel player1 = matchModel.getPlayer1();
        PlayerModel player2 = matchModel.getPlayer2();
        if (player1.getGameScore().getSet() == 2) {
            matchModel.setWinner(player1);
        } else if (player2.getGameScore().getSet() == 2) {
            matchModel.setWinner(player2);
        }
        matchRepository.mergeMatch(matchModel);
    }
    public List<MatchModel> usePaginationHibernate(SessionFactory sessionFactory, int page, String filter) {
        try (Session session = sessionFactory.openSession()){
            int pageSize = 5;
            int firstResult;
            if (page == 1) {
                firstResult = 0;
            } else {
                firstResult = pageSize * (page - 1);
            }
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<MatchModel> criteriaQuery = criteriaBuilder.createQuery(MatchModel.class);
            Root<MatchModel> root = criteriaQuery.from(MatchModel.class);

            // Создаем предикаты для фильтрации по имени игрока
            Join<MatchModel, PlayerModel> player1Join = root.join("Player1");
            Join<MatchModel, PlayerModel> player2Join = root.join("Player2");
            Predicate player1NamePredicate = criteriaBuilder.like(player1Join.get("name"), "%" + filter + "%");
            Predicate player2NamePredicate = criteriaBuilder.like(player2Join.get("name"), "%" + filter + "%");

            // Создаем предикат, который объединяет предикаты по имени игрока
            Predicate playerNamePredicate = criteriaBuilder.or(player1NamePredicate, player2NamePredicate);

            // Добавляем предикат в запрос
            criteriaQuery.where(playerNamePredicate);

            CriteriaQuery<MatchModel> selectQuery = criteriaQuery.select(root);

            TypedQuery<MatchModel> typedQuery = session.createQuery(selectQuery);
            typedQuery.setFirstResult(firstResult);
            typedQuery.setMaxResults(pageSize);

            return typedQuery.getResultList();
        }
    }
    public int quantityPages(SessionFactory sessionFactory, String filter) {
        try (Session session = sessionFactory.openSession()){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);

            Root<MatchModel> root = countQuery.from(MatchModel.class);

            // Создаем предикаты для фильтрации по имени игрока
            Join<MatchModel, PlayerModel> player1Join = root.join("Player1");
            Join<MatchModel, PlayerModel> player2Join = root.join("Player2");
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

