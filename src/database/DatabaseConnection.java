package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_salle_sport";
    private static final String USER = "root";
    private static final String PASSWORD = "zack123!!!";
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading MySQL JDBC driver: " + e.getMessage());
            System.err.println("Please ensure mysql-connector-j-8.2.0.jar is in the classpath");
        }
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);

                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new SQLException("Failed to connect to database. Please check your database configuration.", e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }
}