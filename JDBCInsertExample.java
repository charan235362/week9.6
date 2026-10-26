import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Demonstrates inserting data into a MySQL database using JDBC's
 * PreparedStatement, creating the table first if it does not exist.
 */
public class JDBCInsertExample {

    // Step 2: Constants for JDBC URL, username and password
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "your_password"; // change to your MySQL password

    public static void main(String[] args) {

        String createTableSql = "CREATE TABLE IF NOT EXISTS users ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "name VARCHAR(50) NOT NULL, "
                + "email VARCHAR(100) NOT NULL)";

        String insertSql = "INSERT INTO users (name, email) VALUES (?, ?)";

        // Step 3: try-with-resources to establish the connection
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {

            // Step 4: Create the table if it does not exist
            try (PreparedStatement createStmt = connection.prepareStatement(createTableSql)) {
                createStmt.executeUpdate();
            }

            // Steps 5-7: Insert two records using PreparedStatement
            try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {

                // First record
                insertStmt.setString(1, "John Doe");
                insertStmt.setString(2, "john.doe@example.com");
                insertStmt.executeUpdate();

                // Second record
                insertStmt.setString(1, "Jane Doe");
                insertStmt.setString(2, "jane.doe@example.com");
                insertStmt.executeUpdate();
            }

            // Step 8: Success message
            System.out.println("Data inserted successfully.");

        } catch (SQLException e) {
            // Step 9: Handle SQLException
            System.err.println("Database error occurred!");
            System.err.println("Message   : " + e.getMessage());
            System.err.println("SQL State : " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
        }
        // Step 10: Resources are closed automatically
    }
}

/*
 * ---------------------------------------------------------------
 * DATABASE SETUP (run in MySQL before executing the program)
 * ---------------------------------------------------------------
 * CREATE DATABASE IF NOT EXISTS mydatabase;
 *
 * NOTE: If a users table already exists from the previous lab with
 * columns (id, username, email), either drop it first:
 *     DROP TABLE users;
 * or the INSERT will fail because this program uses a "name" column.
 *
 * ---------------------------------------------------------------
 * COMPILE & RUN
 * ---------------------------------------------------------------
 * javac JDBCInsertExample.java
 * java -cp ".;mysql-connector-java-8.0.33.jar" JDBCInsertExample   (Windows)
 * java -cp ".:mysql-connector-java-8.0.33.jar" JDBCInsertExample   (Linux/Mac)
 *
 * ---------------------------------------------------------------
 * EXPECTED OUTPUT
 * ---------------------------------------------------------------
 * Data inserted successfully.
 *
 * ---------------------------------------------------------------
 * VERIFY IN MYSQL
 * ---------------------------------------------------------------
 * SELECT * FROM users;
 */
