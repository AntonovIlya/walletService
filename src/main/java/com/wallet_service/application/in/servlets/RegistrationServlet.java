package com.wallet_service.application.in.servlets;

import com.wallet_service.application.in.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    @Override
    public void init() {
        UserService userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        System.out.println(login + " " + password);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
