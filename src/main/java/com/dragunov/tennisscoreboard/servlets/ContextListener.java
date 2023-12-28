package com.dragunov.tennisscoreboard.servlets;

import com.dragunov.tennisscoreboard.models.MatchModel;
import com.dragunov.tennisscoreboard.models.PlayerModel;
import com.dragunov.tennisscoreboard.repositories.MatchRepository;
import com.dragunov.tennisscoreboard.repositories.PlayerRepository;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.HashMap;

@WebListener
public class ContextListener implements ServletContextListener {
    public ContextListener(){}

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        HashMap<String, MatchModel> storage = new HashMap<>();
        context.setAttribute("storage", storage);
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(MatchRepository.class)
                .addAnnotatedClass(PlayerModel.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        PlayerRepository playerRepository = new PlayerRepository(sessionFactory);
        MatchRepository matchRepository = new MatchRepository(sessionFactory);
        context.setAttribute("playerRepository", playerRepository);
        context.setAttribute("matchRepository", matchRepository);
    }
}
