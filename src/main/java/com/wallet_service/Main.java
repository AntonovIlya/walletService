package com.wallet_service;

import com.wallet_service.domain.db.DBConnection;

public class Main {
    public static void main(String[] args) {
        //todo удалить .idea
        //todo перенсти старт подключения в репозитории
        DBConnection.startConnection();
        DBConnection.closeConnection();
    }
}