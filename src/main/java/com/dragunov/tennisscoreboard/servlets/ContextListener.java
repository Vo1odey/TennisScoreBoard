package com.dragunov.tennisscoreboard.servlets;

import com.dragunov.tennisscoreboard.models.MatchModel;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.HashMap;

@WebListener
public class ContextListener implements ServletContextListener {
    public ContextListener(){}

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        HashMap<String, MatchModel> storage = new HashMap<>();
        context.setAttribute("storage", storage);
    }
}
