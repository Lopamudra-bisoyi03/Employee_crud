package com.EmployeeCRUD.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseConnector {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnector.class);
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/employee_crud";
    private static final String USER = "root";
    private static final String PASSWORD = "87707@";

    static {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("Failed to load MySQL driver", e);
            throw new RuntimeException("Failed to load MySQL JDBC driver", e);
        }
    }

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            logger.error("Failed to connect to database", e);
            throw new RuntimeException("Failed to connect to the database", e);
        }
        return connection;
    }
}