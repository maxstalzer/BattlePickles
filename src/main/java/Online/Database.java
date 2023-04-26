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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.sql.ResultSet;


public class Database {
    private String databaseURL;
    private String username = "root";
    private String password = "12345678";

    public Database(String s) throws  Exception {

        String databaseUrl = "jdbc:mysql://localhost:3306?serverTimezone=UTC";

        Connection connection = DriverManager.getConnection(databaseUrl, username, password);

        String createDatabaseQuery = String.format("CREATE DATABASE %s",s);
        Statement createDatabaseStatement = connection.createStatement();
        createDatabaseStatement.executeUpdate(createDatabaseQuery);

        String useDatabaseQuery = String.format("USE %s",s);
        Statement useDatabaseStatement = connection.createStatement();
        useDatabaseStatement.executeUpdate(useDatabaseQuery);

        connection.close();

        databaseURL = String.format("jdbc:mysql://localhost:3306/%s?serverTimezone=UTC",s);
    }

    public String getURL() {
        return databaseURL;
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


    public void setupConnection(String databaseName) {
        try {
            String url = String.format("jdbc:mysql://172.20.10.3:3306/%s?serverTimezone=UTC",databaseName);
            String username = "root";
            String password = "12345678";
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM TABLE_NAME");
            while (rs.next()) {
                System.out.println(rs.getString("COLUMN_NAME"));
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }



}

