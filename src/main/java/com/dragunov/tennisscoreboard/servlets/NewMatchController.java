package com.dragunov.tennisscoreboard.servlets;


import com.dragunov.tennisscoreboard.dto.GameScore;
import com.dragunov.tennisscoreboard.models.MatchModel;
import com.dragunov.tennisscoreboard.models.PlayerModel;
import com.dragunov.tennisscoreboard.repositories.MatchRepository;
import com.dragunov.tennisscoreboard.repositories.PlayerRepository;
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
import java.util.HashMap;


@WebServlet(name = "NewMatch", value = "/new-match")
public class NewMatchController extends HttpServlet {
    private HashMap<String, MatchModel> storage;
    @Override
    public void init(ServletConfig config) {
        storage = (HashMap<String, MatchModel>) config.getServletContext().getAttribute("storage");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("NewMatch.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String player1Name = req.getParameter("Player1");
        String player2Name = req.getParameter("Player2");

        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(MatchRepository.class)
                .addAnnotatedClass(PlayerModel.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        PlayerRepository playerRepository = new PlayerRepository(sessionFactory);
        MatchRepository matchRepository = new MatchRepository(sessionFactory);
        playerRepository.addPlayerToH2();
        matchRepository.addMatchToTableScoreBoard();

        //Проверить есть ли игроки с таким именем в БД -> если нет -> создаем

        PlayerModel player1;
        PlayerModel player2;
        if (playerRepository.getPlayerByName(player1Name).isEmpty()) {
            player1 = new PlayerModel(player1Name, new GameScore());
            player1.setId(11);
        } else {
            player1 = playerRepository.getPlayerByName(player1Name).get();
            player1.setGameScore(new GameScore());
        }
        if (playerRepository.getPlayerByName(player2Name).isEmpty()) {
            player2 = new PlayerModel(player2Name, new GameScore());
            player2.setId(12);
        } else {
            player2 = playerRepository.getPlayerByName(player2Name).get();
            player2.setGameScore(new GameScore());
        }
        MatchModel matchModel = new MatchModel(player1, player2);
        String matchId = player1.getName() + "And" + player2.getName();
        storage.put(matchId, matchModel);
        resp.sendRedirect("/match-score?uuid="+matchId);
    }
}
