package com.dragunov.tennisscoreboard.servlets;

import com.dragunov.tennisscoreboard.models.MatchModel;
import com.dragunov.tennisscoreboard.models.PlayerModel;
import com.dragunov.tennisscoreboard.repositories.MatchRepository;
import com.dragunov.tennisscoreboard.repositories.PlayerRepository;
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
import java.util.HashMap;

@WebServlet(name = "MatchScore", value = "/match-score/*")
public class MatchScoreController extends HttpServlet {
    private OngoingMatchesService ongoingMatchesService;
    private PlayerRepository playerRepository;
    private MatchRepository matchRepository;

    @Override
    public void init(ServletConfig config) {
        ongoingMatchesService = (OngoingMatchesService) config.getServletContext().getAttribute("ongoingMatchesService");
        playerRepository = (PlayerRepository) config.getServletContext().getAttribute("playerRepository");
        matchRepository = (MatchRepository) config.getServletContext().getAttribute("matchRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String matchId = req.getParameter("uuid");

        MatchModel matchModel = ongoingMatchesService.getMatch(matchId);
        PlayerModel p1 = matchModel.getPlayer1();
        PlayerModel p2 = matchModel.getPlayer2();

        req.setAttribute("player1", p1);
        req.setAttribute("player2", p2);
        req.setAttribute("uuid", matchId);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/ScoreBoard.jsp");
        dispatcher.forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String matchId = req.getParameter("uuid");
        String playerWin = req.getParameter("player_id");
        int playerWinId = Integer.parseInt(playerWin);
        MatchModel matchModel = ongoingMatchesService.getMatch(matchId);
        PlayerModel p1 = matchModel.getPlayer1();
        PlayerModel p2 = matchModel.getPlayer2();

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();
        String winner;
        if (p1.getId() == playerWinId){
            matchScoreCalculationService.play(p1.getGameScore(), p2.getGameScore());
            if (p1.getGameScore().getSet() == 2) {
                winner = p1.getName();
                req.setAttribute("player1", p1);
                req.setAttribute("player2", p2);
                req.setAttribute("winner", winner);
                req.setAttribute("matchRepository", matchRepository);
                finishedMatchesPersistenceService.saveFinishedMatch(matchRepository, ongoingMatchesService, matchId);
                RequestDispatcher dispatcher = req.getRequestDispatcher("/Winner.jsp");
                dispatcher.forward(req, resp);
            } else {
                resp.sendRedirect("/match-score?uuid=" + matchId);
            }
        }
        if (p2.getId() == playerWinId){
            matchScoreCalculationService.play(p2.getGameScore(), p1.getGameScore());
            if (p2.getGameScore().getSet() == 2) {
                winner = p2.getName();
                req.setAttribute("player1", p1);
                req.setAttribute("player2", p2);
                req.setAttribute("winner", winner);
                req.setAttribute("matchRepository", matchRepository);
                finishedMatchesPersistenceService.saveFinishedMatch(matchRepository, ongoingMatchesService, matchId);
                RequestDispatcher dispatcher = req.getRequestDispatcher("/Winner.jsp");
                dispatcher.forward(req, resp);
            } else {
                resp.sendRedirect("/match-score?uuid=" + matchId);
            }
        }
    }
}
