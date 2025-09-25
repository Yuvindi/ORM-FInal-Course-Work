package lk.ijse.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection dbConnection;
    private static Connection connection;

    // Private constructor to prevent direct instantiation
    private DBConnection() throws SQLException {
        // Load the MySQL JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Establish the database connection
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/elite_driving_school_db", // Replace with your DB URL
                "yuvindi", // Replace with your DB username
                "200435" // Replace with your DB password
        );
    }

    // Public static method to get the singleton instance
    public static DBConnection getInstance() throws SQLException {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    // Method to get the active Connection object
    public Connection getConnection() {
        return connection;
    }
}