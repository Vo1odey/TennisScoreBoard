package com.dragunov.tennisscoreboard.servlets;

import com.dragunov.tennisscoreboard.models.Player;
import com.dragunov.tennisscoreboard.repositories.MatchRepository;
import com.dragunov.tennisscoreboard.repositories.PlayerRepository;
import com.dragunov.tennisscoreboard.services.FinishedMatchesPersistenceService;
import com.dragunov.tennisscoreboard.services.OngoingMatchesService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@WebListener
public class ContextListener implements ServletContextListener {
    public ContextListener(){}

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
        context.setAttribute("ongoingMatchesService", ongoingMatchesService);
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(MatchRepository.class)
                .addAnnotatedClass(Player.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        PlayerRepository playerRepository = new PlayerRepository(sessionFactory);
        MatchRepository matchRepository = new MatchRepository(sessionFactory);
        FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();
        context.setAttribute("finishedMatchesPersistenceService", finishedMatchesPersistenceService);
        context.setAttribute("playerRepository", playerRepository);
        context.setAttribute("matchRepository", matchRepository);
    }
}
