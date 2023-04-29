package Online;

import Base.Board;
import Base.Coordinates;
import Base.Game;
import Base.Gurkins.*;
import Base.Players.Player;
import Base.Tile;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;
import io.cucumber.java.an.Y;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.sql.ResultSet;


public class Database {
    private String databaseURL;
    private final String username = "sigurd";
    private final String password = "12345678";

    public Database(String databaseName) throws  Exception {

        String databaseUrl = "jdbc:mysql://172.20.10.3:3306/";

        Connection connection = DriverManager.getConnection(databaseUrl, username, password);

        String createDatabaseQuery = String.format("CREATE DATABASE %s",databaseName);
        Statement createDatabaseStatement = connection.createStatement();
        createDatabaseStatement.executeUpdate(createDatabaseQuery);
        connection.close();

        databaseURL = String.format("jdbc:mysql://172.20.10.3:3306/%s",databaseName);

        JdbcConnectionSource connectionSource = new JdbcConnectionSource(databaseURL, username, password);

        // Create table for Player class
        Dao<Player, Integer> playerDao = DaoManager.createDao(connectionSource, Player.class);
        TableUtils.createTableIfNotExists(connectionSource, Player.class);

        // Create table for Board class
        Dao<Board, Integer> boardDao = DaoManager.createDao(connectionSource, Board.class);
        TableUtils.createTableIfNotExists(connectionSource, Board.class);

        // Create table for Tile class
        Dao<Tile, Integer> tileDao = DaoManager.createDao(connectionSource, Tile.class);
        TableUtils.createTableIfNotExists(connectionSource, Tile.class);

        // Create table for all instances of the Gurkin class
        Dao<Conichon, Integer> conichonDao = DaoManager.createDao(connectionSource, Conichon.class);
        TableUtils.createTableIfNotExists(connectionSource, Conichon.class);
        Dao<Gherkin, Integer> gherkinDao = DaoManager.createDao(connectionSource, Gherkin.class);
        TableUtils.createTableIfNotExists(connectionSource, Gherkin.class);
        Dao<Pickle, Integer> pickleDao = DaoManager.createDao(connectionSource, Pickle.class);
        TableUtils.createTableIfNotExists(connectionSource, Pickle.class);
        Dao<Yardlong, Integer> yardlongDao = DaoManager.createDao(connectionSource, Yardlong.class);
        TableUtils.createTableIfNotExists(connectionSource, Yardlong.class);
        Dao<Zuchinni, Integer> zuchinniDao = DaoManager.createDao(connectionSource, Zuchinni.class);
        TableUtils.createTableIfNotExists(connectionSource, Zuchinni.class);

        Dao<Game, Integer> gameDao = DaoManager.createDao(connectionSource, Game.class);
        TableUtils.createTableIfNotExists(connectionSource, Game.class);

        connection.close();
    }

    public Database() {

    }

    public void saveGame(Game game) throws Exception {
        JdbcConnectionSource connectionSource = new JdbcConnectionSource(databaseURL, username, password);

        Dao<Player, Integer> playerDao = DaoManager.createDao(connectionSource, Player.class);
        Dao<Board, Integer> boardDao = DaoManager.createDao(connectionSource, Board.class);
        Dao<Tile, Integer> tileDao = DaoManager.createDao(connectionSource, Tile.class);
        Dao<Conichon, Integer> conichonDao = DaoManager.createDao(connectionSource, Conichon.class);
        Dao<Gherkin, Integer> gherkinDao = DaoManager.createDao(connectionSource, Gherkin.class);
        Dao<Pickle, Integer> pickleDao = DaoManager.createDao(connectionSource, Pickle.class);
        Dao<Yardlong, Integer> yardlongDao = DaoManager.createDao(connectionSource, Yardlong.class);
        Dao<Zuchinni, Integer> zuchinniDao = DaoManager.createDao(connectionSource, Zuchinni.class);
        Dao<Game, Integer> gameDao = DaoManager.createDao(connectionSource, Game.class);

        game.setInitial_turn();
        gameDao.create(game);

        Player pl1 = game.getPlayer1();
        Player pl2 = game.getPlayer2();
        playerDao.create(pl1);
        playerDao.create(pl2);

        Board board1 = pl1.getGurkinBoard();
        board1.setUpSaveDatabase();
        boardDao.create(board1);

        conichonDao.create(board1.getConichon());
        gherkinDao.create(board1.getGherkin());
        pickleDao.create(board1.getPickle());
        yardlongDao.create(board1.getYardlong());
        zuchinniDao.create(board1.getZuchinni());

        for (Tile tile : board1.getTiles()) {
            tileDao.create(tile);
        }

        Board board2 = pl2.getGurkinBoard();
        board2.setUpSaveDatabase();
        boardDao.create(board2);

        conichonDao.create(board2.getConichon());
        gherkinDao.create(board2.getGherkin());
        pickleDao.create(board2.getPickle());
        yardlongDao.create(board2.getYardlong());
        zuchinniDao.create(board2.getZuchinni());

        for (Tile tile : board2.getTiles()) {
            tileDao.create(tile);
        }

        boardDao.update(board1);
        boardDao.update(board2);
        playerDao.update(pl1);
        playerDao.update(pl2);
        gameDao.update(game);
        connectionSource.close();
    }

    public Game loadGame(String databaseName) throws Exception {
        this.databaseURL =  String.format("jdbc:mysql://172.20.10.3:3306/%s",databaseName);

        JdbcConnectionSource connectionSource = new JdbcConnectionSource(databaseURL, username, password);

        Dao<Game, Integer> gameDao = DaoManager.createDao(connectionSource, Game.class);

        // Retrieve the game from the database
        Game oldgame = gameDao.queryForId(1);

        Game newgame = new Game();
        newgame.setGameID(oldgame.getGameID());
        newgame.setId(oldgame.getId());
        newgame.setGame_Over(oldgame.getGame_Over());
        newgame.setMultiplayer(oldgame.getMultiplayer());
        newgame.setterInitial_turn(oldgame.getInitial_turn());

        Player player1 = new Player(oldgame.getId(),oldgame.getPlayer1().getName(), oldgame.getPlayer1().getCurrentPlayer());
        player1.setRemaining_gurkins(oldgame.getPlayer1().getRemaining_gurkins());

        Board board1 = new Board();
        board1.setId(oldgame.getPlayer1().getGurkinBoard().getId());
        board1.setConichon(oldgame.getPlayer1().getGurkinBoard().getConichon());
        board1.setGherkin(oldgame.getPlayer1().getGurkinBoard().getGherkin());
        board1.setPickle(oldgame.getPlayer1().getGurkinBoard().getPickle());
        board1.setYardlong(oldgame.getPlayer1().getGurkinBoard().getYardlong());
        board1.setZuchinni(oldgame.getPlayer1().getGurkinBoard().getZuchinni());

        Gurkin conichon1 = new Conichon();
        Gurkin gherkin1 = new Gherkin();
        Gurkin pickle1 = new Pickle();
        Gurkin yardlong1 = new Yardlong();
        Gurkin zuchinni1 = new Zuchinni();

        for (int i = 0; i<10;i++) {
            for (int j = 0; j<10; j++) {
                board1.getTile(new Coordinates(i,j)).setId(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getId());
                board1.getTile(new Coordinates(i,j)).setisGurkin(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getisGurkin());
                board1.getTile(new Coordinates(i,j)).setisHit(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).isHit());
                board1.getTile(new Coordinates(i,j)).setBoard(board1);
                if (oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Conichon) {
                    conichon1.setBoard(board1);
                    conichon1.setLives(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
                    conichon1.setId(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
                    conichon1.setSize(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
                    conichon1.addTile(board1.getTile(new Coordinates(i,j)));
                    board1.getTile(new Coordinates(i,j)).setGurkin(conichon1);
                }
                if (oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Gherkin) {
                    gherkin1.setBoard(board1);
                    gherkin1.setLives(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
                    gherkin1.setId(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
                    gherkin1.setSize(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
                    gherkin1.addTile(board1.getTile(new Coordinates(i,j)));
                    board1.getTile(new Coordinates(i,j)).setGurkin(gherkin1);
                }
                if (oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Pickle) {
                    pickle1.setBoard(board1);
                    pickle1.setLives(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
                    pickle1.setId(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
                    pickle1.setSize(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
                    pickle1.addTile(board1.getTile(new Coordinates(i,j)));
                    board1.getTile(new Coordinates(i,j)).setGurkin(pickle1);

                }
                if (oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Yardlong) {
                    yardlong1.setBoard(board1);
                    yardlong1.setLives(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
                    yardlong1.setId(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
                    yardlong1.setSize(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
                    yardlong1.addTile(board1.getTile(new Coordinates(i,j)));
                    board1.getTile(new Coordinates(i,j)).setGurkin(yardlong1);
                }
                if (oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Zuchinni) {
                    zuchinni1.setBoard(board1);
                    zuchinni1.setLives(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
                    zuchinni1.setId(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
                    zuchinni1.setSize(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
                    zuchinni1.addTile(board1.getTile(new Coordinates(i,j)));
                    board1.getTile(new Coordinates(i,j)).setGurkin(zuchinni1);
                }
            }
        }

        player1.setGurkinBoard(board1);

        newgame.setPlayer1(player1);

        // SET PLAYER 2
        Player player2 = new Player(oldgame.getId(),oldgame.getPlayer2().getName(), oldgame.getPlayer1().getCurrentPlayer());
        player2.setRemaining_gurkins(oldgame.getPlayer2().getRemaining_gurkins());

        Board board2 = new Board();
        board2.setId(oldgame.getPlayer2().getGurkinBoard().getId());
        board2.setConichon(oldgame.getPlayer2().getGurkinBoard().getConichon());
        board2.setGherkin(oldgame.getPlayer2().getGurkinBoard().getGherkin());
        board2.setPickle(oldgame.getPlayer2().getGurkinBoard().getPickle());
        board2.setYardlong(oldgame.getPlayer2().getGurkinBoard().getYardlong());
        board2.setZuchinni(oldgame.getPlayer2().getGurkinBoard().getZuchinni());


        Gurkin conichon2 = new Conichon();
        Gurkin gherkin2 = new Gherkin();
        Gurkin pickle2 = new Pickle();
        Gurkin yardlong2 = new Yardlong();
        Gurkin zuchinni2 = new Zuchinni();

        for (int i = 0; i<10;i++) {
            for (int j = 0; j<10; j++) {
                board2.getTile(new Coordinates(i,j)).setId(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getId());
                board2.getTile(new Coordinates(i,j)).setisGurkin(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getisGurkin());
                board2.getTile(new Coordinates(i,j)).setisHit(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).isHit());
                board2.getTile(new Coordinates(i,j)).setBoard(board2);
                if (oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Conichon) {
                    conichon2.setBoard(board2);
                    conichon2.setLives(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
                    conichon2.setId(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
                    conichon2.setSize(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
                    conichon2.addTile(board2.getTile(new Coordinates(i,j)));
                    board2.getTile(new Coordinates(i,j)).setGurkin(conichon2);
                }
                if (oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Gherkin) {
                    gherkin2.setBoard(board2);
                    gherkin2.setLives(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
                    gherkin2.setId(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
                    gherkin2.setSize(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
                    gherkin2.addTile(board2.getTile(new Coordinates(i,j)));
                    board2.getTile(new Coordinates(i,j)).setGurkin(gherkin2);
                }
                if (oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Pickle) {
                    pickle2.setBoard(board2);
                    pickle2.setLives(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
                    pickle2.setId(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
                    pickle2.setSize(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
                    pickle2.addTile(board2.getTile(new Coordinates(i,j)));
                    board2.getTile(new Coordinates(i,j)).setGurkin(pickle2);
                }
                if (oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Yardlong) {
                    yardlong2.setBoard(board2);
                    yardlong2.setLives(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
                    yardlong2.setId(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
                    yardlong2.setSize(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
                    yardlong2.addTile(board2.getTile(new Coordinates(i,j)));
                    board2.getTile(new Coordinates(i,j)).setGurkin(yardlong2);
                }
                if (oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Zuchinni) {
                    zuchinni2.setBoard(board2);
                    zuchinni2.setLives(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
                    zuchinni2.setId(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
                    zuchinni2.setSize(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
                    zuchinni2.addTile(board2.getTile(new Coordinates(i,j)));
                    board2.getTile(new Coordinates(i,j)).setGurkin(zuchinni2);
                }
            }
        }

        player2.setGurkinBoard(board2);

        newgame.setPlayer2(player2);

        connectionSource.close();
        return newgame;
    }

    public Game loadGame() throws Exception {
        JdbcConnectionSource connectionSource = new JdbcConnectionSource(databaseURL, username, password);


        Dao<Game, Integer> gameDao = DaoManager.createDao(connectionSource, Game.class);
        Dao<Player, Integer> playerDao = DaoManager.createDao(connectionSource, Player.class);
        Dao<Board, Integer> boardDao = DaoManager.createDao(connectionSource, Board.class);
        Dao<Conichon, Integer> conichonDao = DaoManager.createDao(connectionSource, Conichon.class);
        Dao<Gherkin, Integer> gherkinDao = DaoManager.createDao(connectionSource, Gherkin.class);
        Dao<Pickle, Integer> pickleDao = DaoManager.createDao(connectionSource, Pickle.class);
        Dao<Yardlong, Integer> yardlongDao = DaoManager.createDao(connectionSource, Yardlong.class);
        Dao<Zuchinni, Integer> zuchinniDao = DaoManager.createDao(connectionSource, Zuchinni.class);

        // Retrieve the game from the database
        Game game = gameDao.queryForId(1);

        connectionSource.close();

        return game;
    }

    public void updateGame(Game game) throws Exception {
        JdbcConnectionSource connectionSource = new JdbcConnectionSource(databaseURL, username, password);

        Dao<Player, Integer> playerDao = DaoManager.createDao(connectionSource, Player.class);
        Dao<Board, Integer> boardDao = DaoManager.createDao(connectionSource, Board.class);
        Dao<Tile, Integer> tileDao = DaoManager.createDao(connectionSource, Tile.class);
        Dao<Conichon, Integer> conichonDao = DaoManager.createDao(connectionSource, Conichon.class);
        Dao<Gherkin, Integer> gherkinDao = DaoManager.createDao(connectionSource, Gherkin.class);
        Dao<Pickle, Integer> pickleDao = DaoManager.createDao(connectionSource, Pickle.class);
        Dao<Yardlong, Integer> yardlongDao = DaoManager.createDao(connectionSource, Yardlong.class);
        Dao<Zuchinni, Integer> zuchinniDao = DaoManager.createDao(connectionSource, Zuchinni.class);
        Dao<Game, Integer> gameDao = DaoManager.createDao(connectionSource, Game.class);

        game.setInitial_turn();
        gameDao.update(game);

        Player pl1 = game.getPlayer1();
        Player pl2 = game.getPlayer2();
        playerDao.update(pl1);
        playerDao.update(pl2);

        Board board1 = pl1.getGurkinBoard();
        board1.setUpSaveDatabase();
        boardDao.update(board1);

        conichonDao.update(board1.getConichon());
        gherkinDao.update(board1.getGherkin());
        pickleDao.update(board1.getPickle());
        yardlongDao.update(board1.getYardlong());
        zuchinniDao.update(board1.getZuchinni());

        for (Tile tile : board1.getTiles()) {
            tileDao.update(tile);
        }

        Board board2 = pl2.getGurkinBoard();
        board2.setUpSaveDatabase();
        boardDao.update(board2);

        conichonDao.update(board2.getConichon());
        gherkinDao.update(board2.getGherkin());
        pickleDao.update(board2.getPickle());
        yardlongDao.update(board2.getYardlong());
        zuchinniDao.update(board2.getZuchinni());

        for (Tile tile : board2.getTiles()) {
            tileDao.update(tile);
        }

        connectionSource.close();
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

    public List<String> getDatabases() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql://172.20.10.3:3306/", username, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SHOW DATABASES");

        List<String> databases = new ArrayList<>();

        while (resultSet.next()) {
            String databaseName = resultSet.getString("Database");
            if (!((databaseName.equals("information_schema")) || (databaseName.equals("mysql")) || (databaseName.equals("performance_schema")) || (databaseName.equals("sys")))) {
                databases.add(databaseName);
            }
        }
        return databases;
    }

    public void deleteDatabase (String databaseName) throws Exception {
        String databaseUrl = "jdbc:mysql://172.20.10.3:3306/";
        JdbcConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl, username, password);

        try {
            // Close any open database connections
            connectionSource.close();

            // Delete the database using SQL
            Connection connection = DriverManager.getConnection(databaseUrl, username, password);

            String deleteDatabaseQuery = String.format("DROP DATABASE IF EXISTS %s",databaseName);
            Statement deleteDatabaseStatement = connection.createStatement();
            deleteDatabaseStatement.executeUpdate(deleteDatabaseQuery);
            connection.close();
        } catch (SQLException e) {

        } finally {
            // Release the connection source
            connectionSource.closeQuietly();
        }
    }
}

