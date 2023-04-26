package Online;

import java.sql.SQLException;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
public class ServerTest {

        public static void main(String[] args) {

            // The URL of the database on your friend's computer
            String databaseUrl = "jdbc:mysql://172.20.10.3:3306/jdbcdemo";

            // The username and password for the database
            String username = "root";
            String password = "12345678";

            // Create a connection source with the specified URL, username, and password
            JdbcConnectionSource connectionSource = null;
            try {
                connectionSource = new JdbcConnectionSource(databaseUrl, username, password);
            } catch (SQLException e) {
                System.out.println("Failed to create connection source: " + e.getMessage());
                return;
            }

            // Check the connection to the database
            DatabaseConnection connection = null;
            try {
                connection = connectionSource.getReadOnlyConnection("test123");
                System.out.println("Connection established.");
            } catch (SQLException e) {
                System.out.println("Failed to establish connection: " + e.getMessage());
            } finally {
                // Release the connection resources
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception e) {
                        // Ignore any errors that occur while closing the connection
                    }
                }
            }

            // Release the connection source resources
            if (connectionSource != null) {
                try {
                    connectionSource.close();
                } catch (Exception e) {
                    // Ignore any errors that occur while closing the connection source
                }
            }
        }
    }

