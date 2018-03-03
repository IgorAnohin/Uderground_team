package ru.underground.test42.InnerThings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {
        private static Connection connection;

        public enum TypeQuery {
                SELECT,
                UPDATE,
                CREATE;
        }

        public static void init() {

                try {
                        Class.forName("com.mysql.jdbc.Driver");
                        System.out.println("Driver loaded!");
                } catch (ClassNotFoundException e) {
                        throw new IllegalStateException("Cannot find the driver in the classpath!", e);
                }

                String url = "jdbc:mysql://178.162.38.113:3306/test42";
                String username = "stranger";
                String password = "12345";

                try {
                        connection = DriverManager.getConnection(url, username, password);
                } catch (SQLException e) {
                        throw new RuntimeException("Connection is failed.");
                }
        }

        public static ResultSet sendQuery(TypeQuery type, String query) {
                if (connection == null)
                        throw  new RuntimeException("Base doesn't initialized.");

                try {
                        Statement stmt = connection.createStatement();
                        switch (type) {
                                case SELECT: {
                                        return stmt.executeQuery(query);
                                }

                                case UPDATE: {
                                        stmt.executeUpdate(query); // insert, update, delete
                                        break;
                                }

                                case CREATE: {
                                        stmt.execute(query); // сreate table
                                        break;
                                }
                        }
                } catch (SQLException e) {
                        throw new RuntimeException("Something wrong with your query");
                }
                return null;
        }
}
