package entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
public class DatabaseSetup {

    public String DatabaseSetup(String s) throws  Exception {

        String databaseUrl = "jdbc:mysql://localhost:3306?serverTimezone=UTC";
        String databaseUsername = "root";
        String databasePassword = "12345678";

        Connection connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);

        String createDatabaseQuery = String.format("CREATE DATABASE %s",s);
        Statement createDatabaseStatement = connection.createStatement();
        createDatabaseStatement.executeUpdate(createDatabaseQuery);

        String useDatabaseQuery = String.format("USE %s",s);
        Statement useDatabaseStatement = connection.createStatement();
        useDatabaseStatement.executeUpdate(useDatabaseQuery);

        connection.close();

        return String.format("jdbc:mysql://localhost:3306/%s?serverTimezone=UTC",s);
    }
}
