package com.dragunov.tennisscoreboard.servlets;

import com.dragunov.tennisscoreboard.repositories.MatchRepository;
import com.dragunov.tennisscoreboard.repositories.PlayerRepository;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="MainPage", value = "/tennis")
public class MainPageController extends HttpServlet {

    @Override
    public void init(ServletConfig config) {
        PlayerRepository playerRepository = (PlayerRepository) config.getServletContext().getAttribute("playerRepository");
        MatchRepository matchRepository = (MatchRepository) config.getServletContext().getAttribute("matchRepository");
        playerRepository.addPlayerToH2();
        matchRepository.addMatchToTableScoreBoard();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/MainPage.jsp").forward(req, resp);
    }
}
