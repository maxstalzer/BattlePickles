package Base;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;

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
    private String databaseName;

    // The constructer takes a string input and creates a database with the input as its name. After that it creates
    // the necessary tables for storing the game
    //jdbc:mysql://172.20.10.3:3306/
    String jdbcurl = "jdbc:mysql://172.20.10.2/";

    public Database(String databaseName) throws  Exception {

        String databaseUrl = jdbcurl;

        Connection connection = DriverManager.getConnection(databaseUrl, username, password);

        String createDatabaseQuery = String.format("CREATE DATABASE %s",databaseName);
        Statement createDatabaseStatement = connection.createStatement();
        createDatabaseStatement.executeUpdate(createDatabaseQuery);
        connection.close();

        databaseURL = String.format(jdbcurl + "%s",databaseName);
        this.databaseName = databaseName;
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

        Dao<ShotResults, Integer> shotResultsDao = DaoManager.createDao(connectionSource, ShotResults.class);
        TableUtils.createTableIfNotExists(connectionSource, ShotResults.class);

        Dao<Result, Integer> resultDao = DaoManager.createDao(connectionSource, Result.class);
        TableUtils.createTableIfNotExists(connectionSource, Result.class);

        Dao<Coordinates, Integer> coordinates = DaoManager.createDao(connectionSource, Coordinates.class);
        TableUtils.createTableIfNotExists(connectionSource, Coordinates.class);

        connection.close();
    }

    // an empty constructer in case you just want to access the list of databases.
    public Database() {

    }

    // The method saveGame takes a game and store all relevant information about the game in the database.
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
        Dao<ShotResults, Integer> shotResultsDao = DaoManager.createDao(connectionSource, ShotResults.class);
        Dao<Result, Integer> resultDao = DaoManager.createDao(connectionSource, Result.class);
        Dao<Coordinates, Integer> coordinatesDao = DaoManager.createDao(connectionSource, Coordinates.class);

        game.setInitial_turn();
        gameDao.create(game);

        Player pl1 = game.getPlayer1();
        Player pl2 = game.getPlayer2();
        playerDao.create(pl1);
        playerDao.create(pl2);

        ShotResults shotResults1 = pl1.getShotRes();
        shotResults1.toShotCollection();
        shotResultsDao.create(shotResults1);

        for (Result result : shotResults1.getShotCollection()) {
            resultDao.create(result);
        }

        Board board1 = pl1.getGurkinBoard();
        board1.setUpSaveDatabase();
        boardDao.create(board1);

        for (Coordinates coordinates: board1.getFoundCoords()) {
            coordinatesDao.create(coordinates);
        }

        conichonDao.create(board1.getConichon());
        gherkinDao.create(board1.getGherkin());
        pickleDao.create(board1.getPickle());
        yardlongDao.create(board1.getYardlong());
        zuchinniDao.create(board1.getZuchinni());

        for (Tile tile : board1.getTiles()) {
            tileDao.create(tile);
        }

        ShotResults shotResults2 = pl2.getShotRes();
        shotResults2.toShotCollection();
        shotResultsDao.create(shotResults2);

        for (Result result : shotResults2.getShotCollection()) {
            resultDao.create(result);
        }

        Board board2 = pl2.getGurkinBoard();
        board2.setUpSaveDatabase();
        boardDao.create(board2);

        for (Coordinates coordinates: board1.getFoundCoords()) {
            coordinatesDao.create(coordinates);
        }

        conichonDao.create(board2.getConichon());
        gherkinDao.create(board2.getGherkin());
        pickleDao.create(board2.getPickle());
        yardlongDao.create(board2.getYardlong());
        zuchinniDao.create(board2.getZuchinni());

        for (Tile tile : board2.getTiles()) {
            tileDao.create(tile);
        }

        shotResultsDao.update(shotResults1);
        shotResultsDao.update(shotResults2);
        boardDao.update(board1);
        boardDao.update(board2);
        playerDao.update(pl1);
        playerDao.update(pl2);
        gameDao.update(game);
        connectionSource.close();
    }

    public void updateGame(Game game) throws Exception {
        deleteDatabase(databaseName);

        Database database = new Database(databaseName);
        database.saveGame(game);

    }

    // The method loadGame takes a database name and loads a game from that database into a new object Game and returns it.
    public Game loadGame(String databaseName) throws Exception {
        this.databaseName = databaseName;

        this.databaseURL =  String.format(jdbcurl + "%s",databaseName);

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


        Player player1 = new Player(oldgame.getId(), oldgame.getPlayer1().getName(), oldgame.getPlayer1().getCurrentPlayer());
        player1.setRemaining_gurkins(oldgame.getPlayer1().getRemaining_gurkins());

        ShotResults shotResults1 = new ShotResults();
        shotResults1.setId(oldgame.getPlayer1().getShotRes().getId());

        for (Result result : oldgame.getPlayer1().getShotRes().getShotCollection()) {
            Result result1 = new Result();
            result1.setId(result.getId());
            result1.setCharacter(result.getCharacter());
            result1.setX(result.getX());
            result1.setY(result.getY());
            result1.setShotResults(result.getShotResults());
            shotResults1.addShotBoardCollection(result1);
        }
        shotResults1.transformation();
        player1.setShotRes(shotResults1);

        Board board1 = new Board();
        board1.setId(oldgame.getPlayer1().getGurkinBoard().getId());
        board1.setConichon(oldgame.getPlayer1().getGurkinBoard().getConichon());
        board1.setGherkin(oldgame.getPlayer1().getGurkinBoard().getGherkin());
        board1.setPickle(oldgame.getPlayer1().getGurkinBoard().getPickle());
        board1.setYardlong(oldgame.getPlayer1().getGurkinBoard().getYardlong());
        board1.setZuchinni(oldgame.getPlayer1().getGurkinBoard().getZuchinni());

        for (Coordinates coordinates : oldgame.getPlayer1().getGurkinBoard().getFoundCoords()) {
            Coordinates coordinates1 = new Coordinates(coordinates.getX(),coordinates.getY());
            coordinates1.setId(coordinates.getId());
            coordinates1.setBoard(coordinates.getBoard());
            board1.addFoundCoords(coordinates1);
        }

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
        if (!oldgame.getMultiplayer()) {
            AI player2 = new AI(1);
            player2.setId((oldgame.getPlayer2()).getId());
            player2.setCurrentPlayer(oldgame.getPlayer2().getCurrentPlayer());
            player2.setDifficultyString(oldgame.getPlayer2().getDifficultyString());
            player2.setDifficulty(player2.getDifficultyString());
            player2.setName(oldgame.getPlayer2().getName());
            player2.setRemaining_gurkins(oldgame.getPlayer2().getRemaining_gurkins());

            ShotResults shotResults2 = new ShotResults();
            shotResults2.setId(oldgame.getPlayer2().getShotRes().getId());

            for (Result result : oldgame.getPlayer2().getShotRes().getShotCollection()) {
                Result result2 = new Result();
                result2.setId(result.getId());
                result2.setCharacter(result.getCharacter());
                result2.setX(result.getX());
                result2.setY(result.getY());
                result2.setShotResults(result.getShotResults());
                shotResults2.addShotBoardCollection(result2);
            }
            shotResults2.transformation();
            player2.setShotRes(shotResults2);

            Board board2 = new Board();
            board2.setId(oldgame.getPlayer2().getGurkinBoard().getId());
            board2.setConichon(oldgame.getPlayer2().getGurkinBoard().getConichon());
            board2.setGherkin(oldgame.getPlayer2().getGurkinBoard().getGherkin());
            board2.setPickle(oldgame.getPlayer2().getGurkinBoard().getPickle());
            board2.setYardlong(oldgame.getPlayer2().getGurkinBoard().getYardlong());
            board2.setZuchinni(oldgame.getPlayer2().getGurkinBoard().getZuchinni());

            for (Coordinates coordinates : oldgame.getPlayer2().getGurkinBoard().getFoundCoords()) {
                Coordinates coordinates2 = new Coordinates(coordinates.getX(),coordinates.getY());
                coordinates2.setId(coordinates.getId());
                coordinates2.setBoard(coordinates.getBoard());
                board2.addFoundCoords(coordinates2);
            }

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
        } else {
            Player player2 = new Player(oldgame.getId(),oldgame.getPlayer2().getName(), oldgame.getPlayer1().getCurrentPlayer());
            player2.setRemaining_gurkins(oldgame.getPlayer2().getRemaining_gurkins());

            ShotResults shotResults2 = new ShotResults();
            shotResults2.setId(oldgame.getPlayer2().getShotRes().getId());

            for (Result result : oldgame.getPlayer2().getShotRes().getShotCollection()) {
                Result result2 = new Result();
                result2.setId(result.getId());
                result2.setCharacter(result.getCharacter());
                result2.setX(result.getX());
                result2.setY(result.getY());
                result2.setShotResults(result.getShotResults());
                shotResults2.addShotBoardCollection(result2);
            }
            shotResults2.transformation();
            player2.setShotRes(shotResults2);

            Board board2 = new Board();
            board2.setId(oldgame.getPlayer2().getGurkinBoard().getId());
            board2.setConichon(oldgame.getPlayer2().getGurkinBoard().getConichon());
            board2.setGherkin(oldgame.getPlayer2().getGurkinBoard().getGherkin());
            board2.setPickle(oldgame.getPlayer2().getGurkinBoard().getPickle());
            board2.setYardlong(oldgame.getPlayer2().getGurkinBoard().getYardlong());
            board2.setZuchinni(oldgame.getPlayer2().getGurkinBoard().getZuchinni());

            for (Coordinates coordinates : oldgame.getPlayer2().getGurkinBoard().getFoundCoords()) {
                Coordinates coordinates2 = new Coordinates(coordinates.getX(),coordinates.getY());
                coordinates2.setId(coordinates.getId());
                coordinates2.setBoard(coordinates.getBoard());
                board2.addFoundCoords(coordinates2);
            }

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
        }

        connectionSource.close();
        return newgame;
    }

    // This method also loads a game from a database, already known to the database object, into a Game object and returns it
//    public Game loadGame() throws Exception {
//
//        JdbcConnectionSource connectionSource = new JdbcConnectionSource(databaseURL, username, password);
//
//        Dao<Game, Integer> gameDao = DaoManager.createDao(connectionSource, Game.class);
//
//        // Retrieve the game from the database
//        Game oldgame = gameDao.queryForId(1);
//
//        Game newgame = new Game();
//        newgame.setGameID(oldgame.getGameID());
//        newgame.setId(oldgame.getId());
//        newgame.setGame_Over(oldgame.getGame_Over());
//        newgame.setMultiplayer(oldgame.getMultiplayer());
//        newgame.setterInitial_turn(oldgame.getInitial_turn());
//
//
//        Player player1 = new Player(oldgame.getId(), oldgame.getPlayer1().getName(), oldgame.getPlayer1().getCurrentPlayer());
//        player1.setRemaining_gurkins(oldgame.getPlayer1().getRemaining_gurkins());
//
//        ShotResults shotResults1 = new ShotResults();
//        shotResults1.setId(oldgame.getPlayer1().getShotRes().getId());
//
//        for (Result result : oldgame.getPlayer1().getShotRes().getShotCollection()) {
//            Result result1 = new Result();
//            result1.setId(result.getId());
//            result1.setCharacter(result.getCharacter());
//            result1.setX(result.getX());
//            result1.setY(result.getY());
//            result1.setShotResults(result.getShotResults());
//            shotResults1.addShotBoardCollection(result1);
//        }
//        shotResults1.transformation();
//        player1.setShotRes(shotResults1);
//
//        Board board1 = new Board();
//        board1.setId(oldgame.getPlayer1().getGurkinBoard().getId());
//        board1.setConichon(oldgame.getPlayer1().getGurkinBoard().getConichon());
//        board1.setGherkin(oldgame.getPlayer1().getGurkinBoard().getGherkin());
//        board1.setPickle(oldgame.getPlayer1().getGurkinBoard().getPickle());
//        board1.setYardlong(oldgame.getPlayer1().getGurkinBoard().getYardlong());
//        board1.setZuchinni(oldgame.getPlayer1().getGurkinBoard().getZuchinni());
//
//        for (Coordinates coordinates : oldgame.getPlayer1().getGurkinBoard().getFoundCoords()) {
//            Coordinates coordinates1 = new Coordinates(coordinates.getX(),coordinates.getY());
//            coordinates1.setId(coordinates.getId());
//            coordinates1.setBoard(coordinates.getBoard());
//            board1.addFoundCoords(coordinates1);
//        }
//
//        Gurkin conichon1 = new Conichon();
//        Gurkin gherkin1 = new Gherkin();
//        Gurkin pickle1 = new Pickle();
//        Gurkin yardlong1 = new Yardlong();
//        Gurkin zuchinni1 = new Zuchinni();
//
//        for (int i = 0; i<10;i++) {
//            for (int j = 0; j<10; j++) {
//                board1.getTile(new Coordinates(i,j)).setId(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getId());
//                board1.getTile(new Coordinates(i,j)).setisGurkin(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getisGurkin());
//                board1.getTile(new Coordinates(i,j)).setisHit(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).isHit());
//                board1.getTile(new Coordinates(i,j)).setBoard(board1);
//                if (oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Conichon) {
//                    conichon1.setBoard(board1);
//                    conichon1.setLives(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
//                    conichon1.setId(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
//                    conichon1.setSize(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
//                    conichon1.addTile(board1.getTile(new Coordinates(i,j)));
//                    board1.getTile(new Coordinates(i,j)).setGurkin(conichon1);
//                }
//                if (oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Gherkin) {
//                    gherkin1.setBoard(board1);
//                    gherkin1.setLives(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
//                    gherkin1.setId(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
//                    gherkin1.setSize(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
//                    gherkin1.addTile(board1.getTile(new Coordinates(i,j)));
//                    board1.getTile(new Coordinates(i,j)).setGurkin(gherkin1);
//                }
//                if (oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Pickle) {
//                    pickle1.setBoard(board1);
//                    pickle1.setLives(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
//                    pickle1.setId(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
//                    pickle1.setSize(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
//                    pickle1.addTile(board1.getTile(new Coordinates(i,j)));
//                    board1.getTile(new Coordinates(i,j)).setGurkin(pickle1);
//
//                }
//                if (oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Yardlong) {
//                    yardlong1.setBoard(board1);
//                    yardlong1.setLives(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
//                    yardlong1.setId(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
//                    yardlong1.setSize(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
//                    yardlong1.addTile(board1.getTile(new Coordinates(i,j)));
//                    board1.getTile(new Coordinates(i,j)).setGurkin(yardlong1);
//                }
//                if (oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Zuchinni) {
//                    zuchinni1.setBoard(board1);
//                    zuchinni1.setLives(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
//                    zuchinni1.setId(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
//                    zuchinni1.setSize(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
//                    zuchinni1.addTile(board1.getTile(new Coordinates(i,j)));
//                    board1.getTile(new Coordinates(i,j)).setGurkin(zuchinni1);
//                }
//            }
//        }
//
//        player1.setGurkinBoard(board1);
//
//        newgame.setPlayer1(player1);
//
//
//        // SET PLAYER 2
//        if (oldgame.getPlayer2() instanceof AI) {
//            Player player2 = new AI(1);
//            player2.setId(((AI) oldgame.getPlayer2()).getId());
//            player2.setCurrentPlayer(oldgame.getPlayer2().getCurrentPlayer());
//            player2.setDifficultyString(oldgame.getPlayer2().getDifficultyString());
//            ((AI) player2).setDifficulty(player2.getDifficultyString());
//
//            ShotResults shotResults2 = new ShotResults();
//            shotResults2.setId(oldgame.getPlayer2().getShotRes().getId());
//
//            for (Result result : oldgame.getPlayer1().getShotRes().getShotCollection()) {
//                Result result2 = new Result();
//                result2.setId(result.getId());
//                result2.setCharacter(result.getCharacter());
//                result2.setX(result.getX());
//                result2.setY(result.getY());
//                result2.setShotResults(result.getShotResults());
//                shotResults2.addShotBoardCollection(result2);
//            }
//            shotResults2.transformation();
//            player2.setShotRes(shotResults2);
//
//            Board board2 = new Board();
//            board2.setId(oldgame.getPlayer2().getGurkinBoard().getId());
//            board2.setConichon(oldgame.getPlayer2().getGurkinBoard().getConichon());
//            board2.setGherkin(oldgame.getPlayer2().getGurkinBoard().getGherkin());
//            board2.setPickle(oldgame.getPlayer2().getGurkinBoard().getPickle());
//            board2.setYardlong(oldgame.getPlayer2().getGurkinBoard().getYardlong());
//            board2.setZuchinni(oldgame.getPlayer2().getGurkinBoard().getZuchinni());
//
//            for (Coordinates coordinates : oldgame.getPlayer2().getGurkinBoard().getFoundCoords()) {
//                Coordinates coordinates2 = new Coordinates(coordinates.getX(),coordinates.getY());
//                coordinates2.setId(coordinates.getId());
//                coordinates2.setBoard(coordinates.getBoard());
//                board2.addFoundCoords(coordinates2);
//            }
//
//            Gurkin conichon2 = new Conichon();
//            Gurkin gherkin2 = new Gherkin();
//            Gurkin pickle2 = new Pickle();
//            Gurkin yardlong2 = new Yardlong();
//            Gurkin zuchinni2 = new Zuchinni();
//
//            for (int i = 0; i<10;i++) {
//                for (int j = 0; j<10; j++) {
//                    board2.getTile(new Coordinates(i,j)).setId(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getId());
//                    board2.getTile(new Coordinates(i,j)).setisGurkin(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getisGurkin());
//                    board2.getTile(new Coordinates(i,j)).setisHit(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).isHit());
//                    board2.getTile(new Coordinates(i,j)).setBoard(board2);
//                    if (oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Conichon) {
//                        conichon2.setBoard(board2);
//                        conichon2.setLives(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
//                        conichon2.setId(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
//                        conichon2.setSize(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
//                        conichon2.addTile(board2.getTile(new Coordinates(i,j)));
//                        board2.getTile(new Coordinates(i,j)).setGurkin(conichon2);
//                    }
//                    if (oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Gherkin) {
//                        gherkin2.setBoard(board2);
//                        gherkin2.setLives(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
//                        gherkin2.setId(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
//                        gherkin2.setSize(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
//                        gherkin2.addTile(board2.getTile(new Coordinates(i,j)));
//                        board2.getTile(new Coordinates(i,j)).setGurkin(gherkin2);
//                    }
//                    if (oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Pickle) {
//                        pickle2.setBoard(board2);
//                        pickle2.setLives(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
//                        pickle2.setId(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
//                        pickle2.setSize(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
//                        pickle2.addTile(board2.getTile(new Coordinates(i,j)));
//                        board2.getTile(new Coordinates(i,j)).setGurkin(pickle2);
//                    }
//                    if (oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Yardlong) {
//                        yardlong2.setBoard(board2);
//                        yardlong2.setLives(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
//                        yardlong2.setId(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
//                        yardlong2.setSize(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
//                        yardlong2.addTile(board2.getTile(new Coordinates(i,j)));
//                        board2.getTile(new Coordinates(i,j)).setGurkin(yardlong2);
//                    }
//                    if (oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Zuchinni) {
//                        zuchinni2.setBoard(board2);
//                        zuchinni2.setLives(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
//                        zuchinni2.setId(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
//                        zuchinni2.setSize(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
//                        zuchinni2.addTile(board2.getTile(new Coordinates(i,j)));
//                        board2.getTile(new Coordinates(i,j)).setGurkin(zuchinni2);
//                    }
//                }
//            }
//
//            player2.setGurkinBoard(board2);
//
//            newgame.setPlayer2(player2);
//        } else {
//            Player player2 = new Player(oldgame.getId(),oldgame.getPlayer2().getName(), oldgame.getPlayer1().getCurrentPlayer());
//            player2.setRemaining_gurkins(oldgame.getPlayer2().getRemaining_gurkins());
//
//            ShotResults shotResults2 = new ShotResults();
//            shotResults2.setId(oldgame.getPlayer2().getShotRes().getId());
//
//            for (Result result : oldgame.getPlayer1().getShotRes().getShotCollection()) {
//                Result result2 = new Result();
//                result2.setId(result.getId());
//                result2.setCharacter(result.getCharacter());
//                result2.setX(result.getX());
//                result2.setY(result.getY());
//                result2.setShotResults(result.getShotResults());
//                shotResults2.addShotBoardCollection(result2);
//            }
//            shotResults2.transformation();
//            player2.setShotRes(shotResults2);
//
//            Board board2 = new Board();
//            board2.setId(oldgame.getPlayer2().getGurkinBoard().getId());
//            board2.setConichon(oldgame.getPlayer2().getGurkinBoard().getConichon());
//            board2.setGherkin(oldgame.getPlayer2().getGurkinBoard().getGherkin());
//            board2.setPickle(oldgame.getPlayer2().getGurkinBoard().getPickle());
//            board2.setYardlong(oldgame.getPlayer2().getGurkinBoard().getYardlong());
//            board2.setZuchinni(oldgame.getPlayer2().getGurkinBoard().getZuchinni());
//
//            for (Coordinates coordinates : oldgame.getPlayer2().getGurkinBoard().getFoundCoords()) {
//                Coordinates coordinates2 = new Coordinates(coordinates.getX(),coordinates.getY());
//                coordinates2.setId(coordinates.getId());
//                coordinates2.setBoard(coordinates.getBoard());
//                board2.addFoundCoords(coordinates2);
//            }
//
//            Gurkin conichon2 = new Conichon();
//            Gurkin gherkin2 = new Gherkin();
//            Gurkin pickle2 = new Pickle();
//            Gurkin yardlong2 = new Yardlong();
//            Gurkin zuchinni2 = new Zuchinni();
//
//            for (int i = 0; i<10;i++) {
//                for (int j = 0; j<10; j++) {
//                    board2.getTile(new Coordinates(i,j)).setId(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getId());
//                    board2.getTile(new Coordinates(i,j)).setisGurkin(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getisGurkin());
//                    board2.getTile(new Coordinates(i,j)).setisHit(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).isHit());
//                    board2.getTile(new Coordinates(i,j)).setBoard(board2);
//                    if (oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Conichon) {
//                        conichon2.setBoard(board2);
//                        conichon2.setLives(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
//                        conichon2.setId(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
//                        conichon2.setSize(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
//                        conichon2.addTile(board2.getTile(new Coordinates(i,j)));
//                        board2.getTile(new Coordinates(i,j)).setGurkin(conichon2);
//                    }
//                    if (oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Gherkin) {
//                        gherkin2.setBoard(board2);
//                        gherkin2.setLives(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
//                        gherkin2.setId(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
//                        gherkin2.setSize(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
//                        gherkin2.addTile(board2.getTile(new Coordinates(i,j)));
//                        board2.getTile(new Coordinates(i,j)).setGurkin(gherkin2);
//                    }
//                    if (oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Pickle) {
//                        pickle2.setBoard(board2);
//                        pickle2.setLives(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
//                        pickle2.setId(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
//                        pickle2.setSize(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
//                        pickle2.addTile(board2.getTile(new Coordinates(i,j)));
//                        board2.getTile(new Coordinates(i,j)).setGurkin(pickle2);
//                    }
//                    if (oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Yardlong) {
//                        yardlong2.setBoard(board2);
//                        yardlong2.setLives(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
//                        yardlong2.setId(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
//                        yardlong2.setSize(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
//                        yardlong2.addTile(board2.getTile(new Coordinates(i,j)));
//                        board2.getTile(new Coordinates(i,j)).setGurkin(yardlong2);
//                    }
//                    if (oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin() instanceof Zuchinni) {
//                        zuchinni2.setBoard(board2);
//                        zuchinni2.setLives(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
//                        zuchinni2.setId(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
//                        zuchinni2.setSize(oldgame.getPlayer2().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
//                        zuchinni2.addTile(board2.getTile(new Coordinates(i,j)));
//                        board2.getTile(new Coordinates(i,j)).setGurkin(zuchinni2);
//                    }
//                }
//            }
//
//            player2.setGurkinBoard(board2);
//
//            newgame.setPlayer2(player2);
//        }
//
//        connectionSource.close();
//        return newgame;
//    }

    // This method tests if there is a connection to the database
     /*public void TestConnection() {

        // The URL of the database on your friend's computer
        String databaseUrl = "jdbcurl";

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
    }*/
    public Boolean TestConnection(String databaseName) {

        // The URL of the database on your friend's computer
        String databaseUrl = String.format(jdbcurl + "%s",databaseName);

        // The username and password for the database
        String username = "sigurd";
        String password = "12345678";

        // Create a connection source with the specified URL, username, and password
        JdbcConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(databaseUrl, username, password);
        } catch (SQLException e) {
            System.out.println("Failed to create connection source: " + e.getMessage());
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
                    return false;
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
        return true;
    }

    // This method returns a list of all the databases that has been created on the server
    public List<String> getDatabases() throws Exception {
        Connection connection = DriverManager.getConnection(jdbcurl, username, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SHOW DATABASES");

        List<String> databases = new ArrayList<>();

        while (resultSet.next()) {
            String databaseName = resultSet.getString("Database");
            if (!((databaseName.equals("information_schema")) || (databaseName.equals("mysql")) || (databaseName.equals("performance_schema")) || (databaseName.equals("sys")))) {
                databases.add(databaseName);
            }
        }
        connection.close();
        return databases;
    }

    // This method deletes a database from the server that matches the input string
    public void deleteDatabase (String databaseName) throws Exception {
        String databaseUrl = jdbcurl;
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