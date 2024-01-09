package com.dragunov.tennisscoreboard.utils;

import com.dragunov.tennisscoreboard.servlets.NewMatchController;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
    private static MyLogger instance;
    private Logger logger;
    private MyLogger(){
        logger = Logger.getLogger(NewMatchController.class.getName());
        FileHandler fileHandler;
        try {
            fileHandler = new FileHandler("/Users/Java/IdeaProjects/TennisScoreBoard/src/main/java/log/logs.txt");
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.setLevel(Level.WARNING);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static MyLogger getInstance() {
        if (instance == null) {
            instance = new MyLogger();
        }
        return instance;
    }
    public Logger getLogger() {
        return logger;
    }
}
