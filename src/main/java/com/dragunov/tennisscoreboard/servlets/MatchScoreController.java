package com.dragunov.tennisscoreboard.servlets;

import com.dragunov.tennisscoreboard.exceptions.MatchNotFoundException;
import com.dragunov.tennisscoreboard.models.Match;
import com.dragunov.tennisscoreboard.models.Player;
import com.dragunov.tennisscoreboard.repositories.MatchRepository;
import com.dragunov.tennisscoreboard.services.FinishedMatchesPersistenceService;
import com.dragunov.tennisscoreboard.services.MatchScoreCalculationService;
import com.dragunov.tennisscoreboard.services.OngoingMatchesService;
import com.dragunov.tennisscoreboard.utils.MyLogger;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "MatchScore", value = "/match-score/*")
public class MatchScoreController extends HttpServlet {
    private static Logger log = MyLogger.getInstance().getLogger();
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
        log.log(Level.WARNING, "getting UUID");
        try {
            String uuid = validateUUID(req.getParameter("uuid"));
            log.log(Level.WARNING, "getting UUID success");
            Match match = ongoingMatchesService.getMatch(uuid);
            Player p1 = match.getPlayer1();
            Player p2 = match.getPlayer2();
            req.setAttribute("player1", p1);
            req.setAttribute("player2", p2);
            req.setAttribute("uuid", uuid);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/ScoreBoard.jsp");
            dispatcher.forward(req, resp);
        } catch (MatchNotFoundException e) {
            log.log(Level.WARNING, "Fail to getting UUID");
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write(e + " 404 Match not found");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String matchId = req.getParameter("uuid");
        int playerIdWon = Integer.parseInt(req.getParameter("player_id"));
        Match match = ongoingMatchesService.getMatch(matchId);
        Player p1 = match.getPlayer1();
        Player p2 = match.getPlayer2();
        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        if (p1.getId() == playerIdWon){
            matchScoreCalculationService.wonPoint(p1.getGameScore(), p2.getGameScore());
            isPlayerWinMatch(p1, p2, matchId, resp, req);
        } else {
            matchScoreCalculationService.wonPoint(p2.getGameScore(), p1.getGameScore());
            isPlayerWinMatch(p2, p1, matchId, resp, req);
        }
    }

    private void isPlayerWinMatch(Player target, Player player2, String uuid, HttpServletResponse resp,
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
    private String validateUUID(String UUID) throws MatchNotFoundException {
        if (ongoingMatchesService.getMatch(UUID) == null){
            throw new MatchNotFoundException();
        }
        return UUID;
    }
}
