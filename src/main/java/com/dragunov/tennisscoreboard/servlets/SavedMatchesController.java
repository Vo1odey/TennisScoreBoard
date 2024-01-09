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
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Matches", value = "/matches/*")
public class SavedMatchesController extends HttpServlet {
    MatchRepository matchRepository;
    FinishedMatchesPersistenceService finishedMatchesPersistenceService;
    SessionFactory sessionFactory;
    @Override
    public void init(ServletConfig config) {
        matchRepository = (MatchRepository) config.getServletContext().getAttribute("matchRepository");
        sessionFactory = (SessionFactory) config.getServletContext().getAttribute("sessionFactory");
        finishedMatchesPersistenceService = (FinishedMatchesPersistenceService) config.getServletContext()
                .getAttribute("finishedMatchesPersistenceService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filter = req.getParameter("filter_by_player_name");
        int page = Integer.parseInt(req.getParameter("page"));
        List<MatchModel> matchPage = finishedMatchesPersistenceService.usePaginationHibernate(sessionFactory, page, filter);
        int quantityOfPages = finishedMatchesPersistenceService.quantityPages(sessionFactory, filter);
        req.setAttribute("quantityOfPages", quantityOfPages);
        req.setAttribute("filter", filter);
        req.setAttribute("matchPage", matchPage);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/Matches.jsp");
        dispatcher.forward(req, resp);
    }
}
