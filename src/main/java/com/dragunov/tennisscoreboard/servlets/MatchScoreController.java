package com.dragunov.tennisscoreboard.servlets;

import com.dragunov.tennisscoreboard.dto.GameScore;
import com.dragunov.tennisscoreboard.models.MatchModel;
import com.dragunov.tennisscoreboard.models.PlayerModel;
import com.dragunov.tennisscoreboard.repositories.MatchRepository;
import com.dragunov.tennisscoreboard.repositories.PlayerRepository;
import com.dragunov.tennisscoreboard.services.MatchScoreCalculationService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet(name = "MatchScore", value = "/match-score/*")
public class MatchScoreController extends HttpServlet {
    private HashMap<String, MatchModel> storage;

    @Override
    public void init(ServletConfig config) {
        storage = (HashMap<String, MatchModel>) config.getServletContext().getAttribute("storage");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(MatchRepository.class)
                .addAnnotatedClass(PlayerModel.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        PlayerRepository playerRepository = new PlayerRepository(sessionFactory);
        MatchRepository matchRepository = new MatchRepository(sessionFactory);
        playerRepository.addPlayerToH2();
        matchRepository.addMatchToTableScoreBoard();
        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();

        String matchId = req.getParameter("uuid");

        MatchModel matchModel = storage.get(matchId);
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
        MatchModel matchModel = storage.get(matchId);
        PlayerModel p1 = matchModel.getPlayer1();
        PlayerModel p2 = matchModel.getPlayer2();

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        if (p1.getId() == playerWinId){
            matchScoreCalculationService.play(p1.getGameScore(), p2.getGameScore());
            resp.sendRedirect("/match-score?uuid="+matchId);
        }
        if (p2.getId() == playerWinId){
            matchScoreCalculationService.play(p2.getGameScore(), p1.getGameScore());
            resp.sendRedirect("/match-score?uuid="+matchId);
        }
    }
}
