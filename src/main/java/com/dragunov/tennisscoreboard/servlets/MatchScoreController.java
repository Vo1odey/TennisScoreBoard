package com.dragunov.tennisscoreboard.servlets;

import com.dragunov.tennisscoreboard.exceptions.MatchNotFoundException;
import com.dragunov.tennisscoreboard.models.Match;
import com.dragunov.tennisscoreboard.models.Player;
import com.dragunov.tennisscoreboard.repositories.MatchRepository;
import com.dragunov.tennisscoreboard.services.CheckMatchStatus;
import com.dragunov.tennisscoreboard.services.FinishedMatchesPersistenceService;

import com.dragunov.tennisscoreboard.services.OngoingMatchesService;
import com.dragunov.tennisscoreboard.services.matchscore.MatchStatus;
import com.dragunov.tennisscoreboard.utils.MyLogger;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "MatchScore", value = "/match-score/*")
public class MatchScoreController extends HttpServlet {
    private final Logger log = MyLogger.getInstance().getLogger();
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
        try {
            final UUID uuid = uuidCheck(UUID.fromString(req.getParameter("uuid")));
            Match match = ongoingMatchesService.getMatch(uuid);
            req.setAttribute("player1", match.getPlayer1());
            req.setAttribute("player2", match.getPlayer2());
            req.setAttribute("uuid", uuid);
            req.getRequestDispatcher("/ScoreBoard.jsp").forward(req, resp);
        } catch (MatchNotFoundException e) {
            log.log(Level.WARNING, "Fail to getting UUID");
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write(e + " 404 Match not found");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final UUID uuid = UUID.fromString(req.getParameter("uuid"));
        int playerWon = Integer.parseInt(req.getParameter("player_id"));
        Match match = ongoingMatchesService.getMatch(uuid);
        Player first = match.getPlayer1();
        Player second = match.getPlayer2();
        MatchStatus matchStatus = CheckMatchStatus.INSTANCE.getMatchStatus(
                first.getPlayerMatchScore(), second.getPlayerMatchScore());
        if (first.getId() == playerWon){
            first.getPlayerMatchScore().won(matchStatus, second.getPlayerMatchScore());
            isWinMatch(first, match, uuid, resp, req);
        } else {
            second.getPlayerMatchScore().won(matchStatus, first.getPlayerMatchScore());
            isWinMatch(second, match, uuid, resp, req);
        }
    }
    private void isWinMatch(Player wonPlayer, Match match, UUID uuid, HttpServletResponse resp, HttpServletRequest req) throws ServletException, IOException {
        if (wonPlayer.getPlayerMatchScore().getCountSet().getSet() == 2) {
            String winner = wonPlayer.getName();
            req.setAttribute("player1", match.getPlayer1());
            req.setAttribute("player2", match.getPlayer2());
            req.setAttribute("winner", winner);
            finishedMatchesPersistenceService.saveFinishedMatch(matchRepository, ongoingMatchesService, uuid);
            req.getRequestDispatcher("/Winner.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/match-score?uuid=" + uuid);
        }
    }
    private UUID uuidCheck(UUID uuid) throws MatchNotFoundException {
        if (ongoingMatchesService.getMatch(uuid) == null){
            throw new MatchNotFoundException();
        }
        return uuid;
    }
}
