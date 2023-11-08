package com.techit.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final DbAccount dbAccount = new DbAccount();
    private static final String DB_URL = dbAccount.getUrl();
    private static final String DB_USER = dbAccount.getId();
    private static final String DB_PASSWORD = dbAccount.getPw();

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
