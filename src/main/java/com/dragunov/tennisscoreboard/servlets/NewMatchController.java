package com.dragunov.tennisscoreboard.servlets;


import com.dragunov.tennisscoreboard.exceptions.InvalidPlayerNameException;
import com.dragunov.tennisscoreboard.models.Match;
import com.dragunov.tennisscoreboard.models.Player;
import com.dragunov.tennisscoreboard.repositories.PlayerRepository;
import com.dragunov.tennisscoreboard.utils.MyLogger;
import com.dragunov.tennisscoreboard.services.OngoingMatchesService;
import com.dragunov.tennisscoreboard.services.Validation;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.logging.*;

import java.io.IOException;
import java.util.Random;


@WebServlet(name = "NewMatch", value = "/new-match/*")
public class NewMatchController extends HttpServlet {
    private static Logger log = MyLogger.getInstance().getLogger();
    private OngoingMatchesService ongoingMatchesService;
    private PlayerRepository playerRepository;

    @Override
    public void init(ServletConfig config) {
        ongoingMatchesService = (OngoingMatchesService) config.getServletContext().getAttribute("ongoingMatchesService");
        playerRepository = (PlayerRepository) config.getServletContext().getAttribute("playerRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("NewMatch.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstPlayerName;
        String secondPlayerName;
        Validation validation = new Validation();
        try {
            firstPlayerName = validation.validatePlayerName(req.getParameter("Player1"));
            secondPlayerName = validation.validatePlayerName(req.getParameter("Player2"));
            log.log(Level.WARNING, "Validation names - success");
        } catch (InvalidPlayerNameException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(String.valueOf(e));
            log.log(Level.WARNING, "Player name is invalid");
            return;
        }
        Player player1 = playerRepository.getPlayerOrCreateHim(firstPlayerName);
        Player player2 = playerRepository.getPlayerOrCreateHim(secondPlayerName);
        Random random = new Random();
        String UUID = player1.getId() + "" + player2.getId() + random.nextInt(100);
        ongoingMatchesService.recordCurrentMatch(new Match(player1, player2), UUID);
        resp.sendRedirect("/match-score?uuid=" + UUID);
    }
}

