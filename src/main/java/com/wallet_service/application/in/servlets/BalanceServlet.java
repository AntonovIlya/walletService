package com.wallet_service.application.in.servlets;

import com.wallet_service.application.in.service.JWTService;
import com.wallet_service.application.in.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/balance")
public class BalanceServlet extends HttpServlet {

    private UserService userService;
    private JWTService jwtServise;

    @Override
    public void init() {
        userService = new UserService();
        jwtServise = new JWTService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String authorizationHeader = req.getParameter("Authorization");
        if (jwtServise.validateJWT(authorizationHeader)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            //TODO реализовать отправку JSON Error customer message
            return;
        }
        //TODO запрос баланса в сервис
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
