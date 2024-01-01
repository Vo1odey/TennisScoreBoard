package com.dragunov.tennisscoreboard.servlets;

import com.dragunov.tennisscoreboard.models.MatchModel;
import com.dragunov.tennisscoreboard.repositories.MatchRepository;
import com.dragunov.tennisscoreboard.services.FinishedMatchesPersistenceService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Matches", value = "/matches/*")
public class SavedMatchesController  extends HttpServlet {
    MatchRepository matchRepository;
    FinishedMatchesPersistenceService finishedMatchesPersistenceService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        matchRepository = (MatchRepository) config.getServletContext().getAttribute("matchRepository");
        finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filter = req.getParameter("filter_by_player_name");
        int numberOfPages;
        int page = Integer.parseInt(req.getParameter("page"));
        List<MatchModel> matchPage;
        if (filter.isEmpty()){
            matchPage = finishedMatchesPersistenceService.allMatchesInPage(matchRepository, page);
            numberOfPages = finishedMatchesPersistenceService.pagination(matchRepository);
        } else {
            matchPage = finishedMatchesPersistenceService.MatchesInPageByPlayerName(matchRepository, page, filter);
            numberOfPages = finishedMatchesPersistenceService.paginationByFilter(matchPage);
        }
        req.setAttribute("filter", filter);
        req.setAttribute("numberOfPages", numberOfPages);
        req.setAttribute("matchPage", matchPage);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/Matches.jsp");
        dispatcher.forward(req, resp);
    }
}
