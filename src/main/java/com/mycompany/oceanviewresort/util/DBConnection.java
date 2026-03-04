package com.mycompany.oceanviewresort.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static volatile Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/oceanviewresort";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private DBConnection() {}

    public static Connection getConnection() {
        try {
            Connection local = connection;
            if (local == null || local.isClosed()) {
                synchronized (DBConnection.class) {
                    local = connection;
                    if (local == null || local.isClosed()) {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        local = DriverManager.getConnection(URL, USER, PASSWORD);
                        connection = local;
                    }
                }
            }
            return local;
        } catch (ClassNotFoundException | SQLException e) {
            throw new IllegalStateException("Failed to initialize database connection", e);
        }
    }
}