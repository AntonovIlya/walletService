package com.wallet_service;

import com.wallet_service.application.in.service.UserService;
import com.wallet_service.domain.db.DBConnection;

public class Main {
    public static void main(String[] args) {
        DBConnection.startConnection();
        UserService service = new UserService();
        service.start();
        DBConnection.closeConnection();
    }
}