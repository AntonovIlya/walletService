package com.wallet_service;

import com.wallet_service.application.in.service.UserService;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserService();
        service.start();
    }
}