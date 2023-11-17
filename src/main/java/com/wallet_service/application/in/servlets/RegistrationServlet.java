package com.wallet_service.application.in.servlets;

import com.google.gson.Gson;
import com.wallet_service.application.in.service.JWTService;
import com.wallet_service.application.in.service.UserService;
import com.wallet_service.domain.dto.JwtDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    //todo добавить документацию
    private UserService userService;
    private JWTService jwtService;

    @Override
    public void init() {
        userService = new UserService();
        jwtService = new JWTService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            userService.signUp(login, password);
        } catch (RuntimeException e) {
            //todo вернуть ошибку регистрации
            //Создать класс исключения для будущей реализации Spring
            //Если принимается исключение вернуть в соответствии со спецификацией ответ
            return;
        }

        JwtDTO jwtDTO = jwtService.getJwtDTO(login);
        String jwtJSON = new Gson().toJson(jwtDTO);

        PrintWriter out;
        try {
            out = resp.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(jwtJSON);
        out.flush();
    }
}
