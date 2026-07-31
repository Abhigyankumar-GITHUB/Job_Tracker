package com.jobtracker.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Database details environment variables se aayengi
	private static final String HOST = System.getenv("DB_HOST");
	private static final String PORT = System.getenv("DB_PORT");
	private static final String DB_NAME = System.getenv("DB_NAME");
	private static final String USER = System.getenv("DB_USERNAME");
	private static final String PASSWORD = System.getenv("DB_PASSWORD");

	private static final String URL =
	        "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME
	        + "?sslMode=REQUIRED";


    // MySQL JDBC Driver load
    static {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {

            throw new RuntimeException(
                    "MySQL JDBC Driver not found!",
                    e
            );
        }
    }


    // Database connection return karega
    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}