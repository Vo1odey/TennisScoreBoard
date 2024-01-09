package com.dragunov.tennisscoreboard.servlets;

import com.dragunov.tennisscoreboard.models.MatchModel;
import com.dragunov.tennisscoreboard.models.PlayerModel;
import com.dragunov.tennisscoreboard.repositories.MatchRepository;
import com.dragunov.tennisscoreboard.services.FinishedMatchesPersistenceService;
import com.dragunov.tennisscoreboard.services.MatchScoreCalculationService;
import com.dragunov.tennisscoreboard.services.OngoingMatchesService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "MatchScore", value = "/match-score/*")
public class MatchScoreController extends HttpServlet {
    private OngoingMatchesService ongoingMatchesService;
    private MatchRepository matchRepository;
    private FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    @Override
    public void init(ServletConfig config) {
        ongoingMatchesService = (OngoingMatchesService) config.getServletContext().getAttribute("ongoingMatchesService");
        finishedMatchesPersistenceService = (FinishedMatchesPersistenceService) config
                .getServletContext().getAttribute("finishedMatchesPersistenceService");
        matchRepository = (MatchRepository) config.getServletContext().getAttribute("matchRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        MatchModel matchModel = ongoingMatchesService.getMatch(uuid);
        PlayerModel p1 = matchModel.getPlayer1();
        PlayerModel p2 = matchModel.getPlayer2();
        req.setAttribute("player1", p1);
        req.setAttribute("player2", p2);
        req.setAttribute("uuid", uuid);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/ScoreBoard.jsp");
        dispatcher.forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String matchId = req.getParameter("uuid");
        int playerIdWon = Integer.parseInt(req.getParameter("player_id"));
        MatchModel matchModel = ongoingMatchesService.getMatch(matchId);
        PlayerModel p1 = matchModel.getPlayer1();
        PlayerModel p2 = matchModel.getPlayer2();
        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        if (p1.getId() == playerIdWon){
            matchScoreCalculationService.play(p1.getGameScore(), p2.getGameScore());
            isPlayerWinMatch(p1, p2, matchId, resp, req);
        }
        if (p2.getId() == playerIdWon){
            matchScoreCalculationService.play(p2.getGameScore(), p1.getGameScore());
            isPlayerWinMatch(p2, p1, matchId, resp, req);
        }
    }

    private void isPlayerWinMatch(PlayerModel target, PlayerModel player2, String uuid, HttpServletResponse resp,
                                  HttpServletRequest req) throws ServletException, IOException {
        if (target.getGameScore().getSet() == 2) {
            String winner = target.getName();
            req.setAttribute("player1", target);
            req.setAttribute("player2", player2);
            req.setAttribute("winner", winner);
            req.setAttribute("matchRepository", matchRepository);
            finishedMatchesPersistenceService.saveFinishedMatch(matchRepository, ongoingMatchesService, uuid);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/Winner.jsp");
            dispatcher.forward(req, resp);
        } else {
            resp.sendRedirect("/match-score?uuid=" + uuid);
        }
    }
}
