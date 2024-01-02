package com.dragunov.tennisscoreboard.services;

import com.dragunov.tennisscoreboard.models.MatchModel;
import com.dragunov.tennisscoreboard.models.PlayerModel;
import com.dragunov.tennisscoreboard.repositories.MatchRepository;

import java.util.ArrayList;
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

    public int pagination (MatchRepository matchRepository) {
        final int COUNT = 5;
        List<MatchModel> matches = matchRepository.getMatches();
        int pages = (int) Math.ceil((double) matches.size() / COUNT);
        if (pages == 0) return 1;
        return pages;
    }
    public int paginationByFilter (List<MatchModel> filterMatches) {
        //передаем заполненный лист с фильтром
        final int COUNT = 5;
        int pages = (int) Math.ceil((double) filterMatches.size() / COUNT);
        if (pages == 0) return 1;
        return pages;
    }
    public List<MatchModel> allMatchesInPage(MatchRepository matchRepository, int page) {
        List<MatchModel> matchesInPage = new ArrayList<>();
        int matchesOnThePage = 5;
        int allPages = pagination(matchRepository);
        int previousPage = page - 1;
        int beginID;
        int endID;
        for (int pageNumber = 1; pageNumber <= allPages; pageNumber++) {
            if (pageNumber == page) {
                endID = matchesOnThePage * pageNumber;
                beginID = endID - (endID - (matchesOnThePage * previousPage + 1));
                while (beginID <= endID) {
                    if (matchRepository.getMatch(beginID).isPresent()) {
                        matchesInPage.add(matchRepository.getMatch(beginID).get());
                    }
                    beginID++;
                }
            }
        }
        return matchesInPage;
    }
    public List<MatchModel> MatchesInPageByPlayerName(MatchRepository matchRepository, int page, String filter) {
        List<MatchModel> matchesInPage = new ArrayList<>();
        int matchesOnThePage = 5;
        int allPages = pagination(matchRepository);
        int previousPage = page - 1;
        int beginID;
        int endID;
        for (int pageNumber = 1; pageNumber <= allPages; pageNumber++) {
            if (pageNumber == page) {
                endID = matchesOnThePage * pageNumber;
                beginID = endID - (endID - (matchesOnThePage * previousPage + 1));
                while (beginID <= endID) {
                    //if filter.isEmpty -> matchesInPage.add(matchRepository.getMatch(beginID).get());
                    //else -> matchRepository.getMatchByPlayerName(filter);
                    matchesInPage = matchRepository.getMatchByPlayerName(filter);
                    beginID++;
                }
            }
        }
        return matchesInPage;
    }
}

