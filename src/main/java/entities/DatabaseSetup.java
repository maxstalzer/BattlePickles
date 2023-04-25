package entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class DatabaseSetup {
        public static void main(String[] args) throws SQLException {
            String databaseUrl = "jdbc:mysql://localhost:3306";
            String databaseUsername = "username";
            String databasePassword = "password";

            Connection connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);

            String createDatabaseQuery = "CREATE DATABASE game_db";
            Statement createDatabaseStatement = connection.createStatement();
            createDatabaseStatement.executeUpdate(createDatabaseQuery);

            String useDatabaseQuery = "USE game_db";
            Statement useDatabaseStatement = connection.createStatement();
            useDatabaseStatement.executeUpdate(useDatabaseQuery);

            String createGameStateTableQuery = "CREATE TABLE game_state (" +
                    "id INT(11) PRIMARY KEY AUTO_INCREMENT," +
                    "current_player_id INT(11)," +
                    "game_data TEXT)";
            Statement createGameStateTableStatement = connection.createStatement();
            createGameStateTableStatement.executeUpdate(createGameStateTableQuery);

            String createPlayersTableQuery = "CREATE TABLE players (" +
                    "id INT(11) PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(255)," +
                    "is_current_player BOOLEAN)";
            Statement createPlayersTableStatement = connection.createStatement();
            createPlayersTableStatement.executeUpdate(createPlayersTableQuery);

            connection.close();
        }
    }
}
