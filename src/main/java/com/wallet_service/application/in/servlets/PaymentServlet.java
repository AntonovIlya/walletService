package com.wallet_service.application.in.servlets;

import com.wallet_service.application.in.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {

    @Override
    public void init() {
        UserService userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        //TODO реализовать валидацию токена
        //TODO реализовать валидацию входящих DTO
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
