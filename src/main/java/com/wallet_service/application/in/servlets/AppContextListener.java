package com.wallet_service.application.in.servlets;

import com.wallet_service.domain.db.DBConnection;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DBConnection.startConnection();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBConnection.closeConnection();
    }
}
