package Online;

import Base.Board;
import Base.Gurkins.Gurkin;
import Base.Players.Player;
import Base.Tile;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.sql.ResultSet;


public class Database {
    private String databaseURL;
    private String username = "sigurd";
    private String password = "12345678";

    public Database(String databaseName) throws  Exception {

        String databaseUrl = "jdbc:mysql://172.20.10.3:3306";

        Connection connection = DriverManager.getConnection(databaseUrl, username, password);

        String createDatabaseQuery = String.format("CREATE DATABASE %s",databaseName);
        Statement createDatabaseStatement = connection.createStatement();
        createDatabaseStatement.executeUpdate(createDatabaseQuery);

        String useDatabaseQuery = String.format("USE %s",databaseName);
        Statement useDatabaseStatement = connection.createStatement();
        useDatabaseStatement.executeUpdate(useDatabaseQuery);
        connection.close();

        databaseURL = String.format("jdbc:mysql://172.20.10.3:3306/%s",databaseName);
    }

    public void updatePlayer(Player player) throws Exception {
        JdbcConnectionSource connectionSource = new JdbcConnectionSource(databaseURL, username, password);
        Dao<Player, Integer> playerDao = DaoManager.createDao(connectionSource, Player.class);
        Dao<Board, Integer> boardDao = DaoManager.createDao(connectionSource, Board.class);
        Dao<Tile, Integer> tileDao = DaoManager.createDao(connectionSource, Tile.class);
        Dao<Gurkin, Integer> gurkinDao = DaoManager.createDao(connectionSource, Gurkin.class);

        // Update player and its associated objects
        playerDao.update(player);
        Board board = player.getGurkinBoard();
        boardDao.update(board);
        for (Tile tile : board.getTiles()) {
            tileDao.update(tile);
            if (tile.hasGurkin()) {
                gurkinDao.update(tile.getGurkin());
            }
        }
        connectionSource.close();
    }

    public Player retrievePlayer(int id) throws Exception {
        JdbcConnectionSource connectionSource = new JdbcConnectionSource(databaseURL, username, password);

        // Retrieve the player with the given ID from the database
        Dao<Player, Integer> playerDao = DaoManager.createDao(connectionSource, Player.class);
        Player player = playerDao.queryForId(id);

        // Retrieve the player's associated board from the database
        Dao<Board, Integer> boardDao = DaoManager.createDao(connectionSource, Board.class);
        Board board = boardDao.queryForId(player.getGurkinBoard().getId());

        // Retrieve all tiles associated with this board from the database
        Dao<Tile, Integer> tileDao = DaoManager.createDao(connectionSource, Tile.class);
        QueryBuilder<Tile, Integer> tileQueryBuilder = tileDao.queryBuilder();
        tileQueryBuilder.where().eq("board_id", board.getId());
        Collection<Tile> tiles = tileDao.query(tileQueryBuilder.prepare());

        // Retrieve all gurkins associated with each tile from the database
        Dao<Gurkin, Integer> gurkinDao = DaoManager.createDao(connectionSource, Gurkin.class);
        for (Tile tile : tiles) {
            QueryBuilder<Gurkin, Integer> gurkinQueryBuilder = gurkinDao.queryBuilder();
            gurkinQueryBuilder.where().eq("tile_id", tile.getId());
            List<Gurkin> gurkins = gurkinDao.query(gurkinQueryBuilder.prepare());
            tile.setGurkin(gurkins.get(0));
        }

        // Set the retrieved board and tiles to the player object
        board.setTiles(tiles);
        player.setGurkinBoard(board);

        connectionSource.close();
        return player;
    }


    public void TestConnection(String databaseName) {

        // The URL of the database on your friend's computer
        String databaseUrl = String.format("jdbc:mysql://172.20.10.3:3306/%s",databaseName);

        // The username and password for the database
        String username = "sigurd";
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
            connection = connectionSource.getReadOnlyConnection("*");
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

