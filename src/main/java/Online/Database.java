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


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.sql.ResultSet;


public class Database {
    private String databaseURL;
    private String username = "sigurd";
    private String password = "12345678";

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


        Player pl1 = game.getPlayer1();
        Player pl2 = game.getPlayer2();

        game.setInitial_turn();

        Board board1 = pl1.getGurkinBoard();

        boardDao.create(board1);

        Set<Conichon> conichon1 = new HashSet<Conichon>();
        Set<Gherkin> gherkin1 = new HashSet<Gherkin>();
        Set<Pickle> pickle1 = new HashSet<Pickle>();
        Set<Yardlong> yardlong1 = new HashSet<Yardlong>();
        Set<Zuchinni> zuchinni1 = new HashSet<Zuchinni>();


        for (Tile tile : board1.getTiles()) {
            if (tile.hasGurkin()) {
                if (tile.getGurkin() instanceof Conichon) {
                    tile.setChar(tile.getGurkin());
                    conichon1.add((Conichon) tile.getGurkin());
                } else if (tile.getGurkin() instanceof Gherkin) {
                    tile.setChar(tile.getGurkin());
                    gherkin1.add((Gherkin) tile.getGurkin());
                } else if (tile.getGurkin() instanceof Pickle) {
                    tile.setChar(tile.getGurkin());
                    pickle1.add((Pickle) tile.getGurkin());
                } else if (tile.getGurkin() instanceof Yardlong) {
                    tile.setChar(tile.getGurkin());
                    yardlong1.add((Yardlong) tile.getGurkin());
                } else if (tile.getGurkin() instanceof Zuchinni) {
                    tile.setChar(tile.getGurkin());
                    zuchinni1.add((Zuchinni) tile.getGurkin());
                }

            }
        }

        for (Conichon conichon : conichon1) {
            conichonDao.create(conichon);
        }
        for (Gherkin gherkin : gherkin1) {
            gherkinDao.create(gherkin);
        }
        for (Pickle pickle : pickle1) {
            pickleDao.create(pickle);
        }
        for (Yardlong yardlong : yardlong1) {
            yardlongDao.create(yardlong);
        }
        for (Zuchinni zuchinni : zuchinni1) {
            zuchinniDao.create(zuchinni);
        }

        for (Tile tile : board1.getTiles()) {
            tile.setBoard(board1);
            tileDao.create(tile);
        }

        Board board2 = pl2.getGurkinBoard();

        boardDao.create(board2);

        Set<Conichon> conichon2 = new HashSet<Conichon>();
        Set<Gherkin> gherkin2 = new HashSet<Gherkin>();
        Set<Pickle> pickle2 = new HashSet<Pickle>();
        Set<Yardlong> yardlong2 = new HashSet<Yardlong>();
        Set<Zuchinni> zuchinni2 = new HashSet<Zuchinni>();

        for (Tile tile : board2.getTiles()) {
            if (tile.hasGurkin()) {
                if (tile.getGurkin() instanceof Conichon) {
                    tile.setChar(tile.getGurkin());
                    conichon2.add((Conichon) tile.getGurkin());
                } else if (tile.getGurkin() instanceof Gherkin) {
                    tile.setChar(tile.getGurkin());
                    gherkin2.add((Gherkin) tile.getGurkin());
                } else if (tile.getGurkin() instanceof Pickle) {
                    tile.setChar(tile.getGurkin());
                    pickle2.add((Pickle) tile.getGurkin());
                } else if (tile.getGurkin() instanceof Yardlong) {
                    tile.setChar(tile.getGurkin());
                    yardlong2.add((Yardlong) tile.getGurkin());
                } else if (tile.getGurkin() instanceof Zuchinni) {
                    tile.setChar(tile.getGurkin());
                    zuchinni2.add((Zuchinni) tile.getGurkin());
                }

            }
        }

        for (Conichon conichon : conichon2) {
            conichonDao.create(conichon);
        }
        for (Gherkin gherkin : gherkin2) {
            gherkinDao.create(gherkin);
        }
        for (Pickle pickle : pickle2) {
            pickleDao.create(pickle);
        }
        for (Yardlong yardlong : yardlong2) {
            yardlongDao.create(yardlong);
        }
        for (Zuchinni zuchinni : zuchinni2) {
            zuchinniDao.create(zuchinni);
        }

        for (Tile tile : board2.getTiles()) {
            tile.setBoard(board2);
            tileDao.create(tile);
        }


        playerDao.create(pl1);
        playerDao.create(pl2);
        gameDao.create(game);
        connectionSource.close();
    }

    public Game loadGame(String databaseName) throws Exception {
        this.databaseURL =  String.format("jdbc:mysql://172.20.10.3:3306/%s",databaseName);

        JdbcConnectionSource connectionSource = new JdbcConnectionSource(databaseURL, username, password);

        Dao<Conichon, Integer> conichonDao = DaoManager.createDao(connectionSource, Conichon.class);
        Dao<Gherkin, Integer> gherkinDao = DaoManager.createDao(connectionSource, Gherkin.class);
        Dao<Pickle, Integer> pickleDao = DaoManager.createDao(connectionSource, Pickle.class);
        Dao<Yardlong, Integer> yardlongDao = DaoManager.createDao(connectionSource, Yardlong.class);
        Dao<Zuchinni, Integer> zuchinniDao = DaoManager.createDao(connectionSource, Zuchinni.class);

        // Retrieve the game from the database
        Dao<Game, Integer> gameDao = DaoManager.createDao(connectionSource, Game.class);


        // Retrieve all players associated with the game from the database
        Dao<Player, Integer> playerDao = DaoManager.createDao(connectionSource, Player.class);


        // Retrieve all boards associated with each player from the database
        Dao<Board, Integer> boardDao = DaoManager.createDao(connectionSource, Board.class);

        Dao<Tile, Integer> tileDao = DaoManager.createDao(connectionSource, Tile.class);


        List<Player> players = playerDao.queryForAll();

        Board board1 = boardDao.queryForId(players.get(0).getGurkinBoard().getId());

        System.out.println("-----------" + board1.getId() + "---------");

        // Retrieve all tiles associated with this board from the database
        QueryBuilder<Tile, Integer> queryBuilder1 = tileDao.queryBuilder();
        queryBuilder1.where().eq("board_id", board1.getId()).and().isNull("gurkinID_id");
        Collection<Tile> tiles1 = queryBuilder1.query();


        QueryBuilder<Tile, Integer> queryC = tileDao.queryBuilder();
        queryC.where().eq("board_id", board1.getId()).and().eq("gurkinChar", "c");
        List<Tile> tilesC = queryC.query();

        for (Tile tile : tilesC) {
            tile.setGurkin(conichonDao.queryForId(0));
            tiles1.add(tile);
        }
        QueryBuilder<Tile, Integer> queryG = tileDao.queryBuilder();
        queryG.where().eq("board_id", board1.getId()).and().eq("gurkinChar", "g");
        List<Tile> tilesG = queryC.query();

        for (Tile tile : tilesG) {
            tile.setGurkin(gherkinDao.queryForId(0));
            tiles1.add(tile);
        }
        QueryBuilder<Tile, Integer> queryP = tileDao.queryBuilder();
        queryP.where().eq("board_id", board1.getId()).and().eq("gurkinChar", "p");
        List<Tile> tilesP = queryC.query();

        for (Tile tile : tilesP) {
            tile.setGurkin(pickleDao.queryForId(0));
            System.out.println("--------super---------" + tile.getX() + "------" + tile.getY() + "---------------------");
            tiles1.add(tile);
        }
        QueryBuilder<Tile, Integer> queryY = tileDao.queryBuilder();
        queryY.where().eq("board_id", board1.getId()).and().eq("gurkinChar", "y");
        List<Tile> tilesY = queryC.query();

        for (Tile tile : tilesY) {
            tile.setGurkin(yardlongDao.queryForId(0));
            tiles1.add(tile);
        }
        QueryBuilder<Tile, Integer> queryZ = tileDao.queryBuilder();
        queryZ.where().eq("board_id", board1.getId()).and().eq("gurkinChar", "z");
        List<Tile> tilesZ = queryC.query();

        for (Tile tile : tilesZ) {
            tile.setGurkin(zuchinniDao.queryForId(0));
            tiles1.add(tile);
        }
        System.out.println("-------------------" + tiles1.size() + "---------------");
        board1.setTiles(tiles1);
        System.out.println("-------------------" + board1.getTile(new Coordinates(1,1)).getGurkin().getLives() + "---------------");

        Board board2 = boardDao.queryForId(players.get(1).getGurkinBoard().getId());
        System.out.println(board2.getId());

        QueryBuilder<Tile, Integer> queryBuilder2 = tileDao.queryBuilder();
        queryBuilder2.where().eq("board_id", board2.getId()).and().isNull("gurkinID_id");
        Collection<Tile> tiles2 = queryBuilder2.query();

        QueryBuilder<Tile, Integer> queryC2 = tileDao.queryBuilder();
        queryC2.where().eq("board_id", board2.getId()).and().eq("gurkinChar", "c");
        List<Tile> tilesC2 = queryC.query();

        for (Tile tile : tilesC2) {
            tile.setGurkin(conichonDao.queryForId(0));
            tiles2.add(tile);
        }
        QueryBuilder<Tile, Integer> queryG2 = tileDao.queryBuilder();
        queryG2.where().eq("board_id", board2.getId()).and().eq("gurkinChar", "g");
        List<Tile> tilesG2 = queryC.query();

        for (Tile tile : tilesG2) {
            tile.setGurkin(gherkinDao.queryForId(0));
            tiles2.add(tile);
        }
        QueryBuilder<Tile, Integer> queryP2 = tileDao.queryBuilder();
        queryP2.where().eq("board_id", board2.getId()).and().eq("gurkinChar", "p");
        List<Tile> tilesP2 = queryC.query();

        for (Tile tile : tilesP2) {
            tile.setGurkin(pickleDao.queryForId(0));
            tiles2.add(tile);
        }
        QueryBuilder<Tile, Integer> queryY2 = tileDao.queryBuilder();
        queryY2.where().eq("board_id", board2.getId()).and().eq("gurkinChar", "y");
        List<Tile> tilesY2 = queryC.query();

        for (Tile tile : tilesY2) {
            tile.setGurkin(yardlongDao.queryForId(0));
            tiles2.add(tile);
        }
        QueryBuilder<Tile, Integer> queryZ2 = tileDao.queryBuilder();
        queryZ2.where().eq("board_id", board2.getId()).and().eq("gurkinChar", "z");
        List<Tile> tilesZ2 = queryC.query();

        for (Tile tile : tilesZ2) {
            tile.setGurkin(zuchinniDao.queryForId(0));
            tiles2.add(tile);
        }

        board2.setTiles(tiles2);


        // Set the retrieved board and tiles to the player object
        players.get(0).setGurkinBoard(board1);
        players.get(1).setGurkinBoard(board2);

        Game game = gameDao.queryForId(1);
        game.setPlayer1(players.get(1));
        game.setPlayer2(players.get(0));

        connectionSource.close();


        return game;
    }

    public Game loadGame() throws Exception {
        JdbcConnectionSource connectionSource = new JdbcConnectionSource(databaseURL, username, password);

        // Retrieve the game from the database
        Dao<Game, Integer> gameDao = DaoManager.createDao(connectionSource, Game.class);
        Game game = gameDao.queryForId(1);

        // Retrieve all players associated with the game from the database
        Dao<Player, Integer> playerDao = DaoManager.createDao(connectionSource, Player.class);
        List<Player> players = playerDao.queryForAll();

        // Retrieve all boards associated with each player from the database
        Dao<Board, Integer> boardDao = DaoManager.createDao(connectionSource, Board.class);

        Board board1 = boardDao.queryForId(players.get(1).getGurkinBoard().getId());

        // Retrieve all tiles associated with this board from the database
        Dao<Tile, Integer> tileDao = DaoManager.createDao(connectionSource, Tile.class);
        QueryBuilder<Tile, Integer> queryBuilder1 = tileDao.queryBuilder();
        queryBuilder1.where().eq("board_id", board1.getId()).and().isNull("gurkinID_id");
        Collection<Tile> tiles1 = queryBuilder1.query();

        Dao<Conichon, Integer> conichonDao = DaoManager.createDao(connectionSource, Conichon.class);
        Dao<Gherkin, Integer> gherkinDao = DaoManager.createDao(connectionSource, Gherkin.class);
        Dao<Pickle, Integer> pickleDao = DaoManager.createDao(connectionSource, Pickle.class);
        Dao<Yardlong, Integer> yardlongDao = DaoManager.createDao(connectionSource, Yardlong.class);
        Dao<Zuchinni, Integer> zuchinniDao = DaoManager.createDao(connectionSource, Zuchinni.class);

        QueryBuilder<Tile, Integer> queryC = tileDao.queryBuilder();
        queryC.where().eq("board_id", board1.getId()).and().eq("gurkinChar", "c");
        List<Tile> tilesC = queryC.query();

        for (Tile tile : tilesC) {
            tile.setGurkin(conichonDao.queryForId(1));
            tiles1.add(tile);
        }
        QueryBuilder<Tile, Integer> queryG = tileDao.queryBuilder();
        queryG.where().eq("board_id", board1.getId()).and().eq("gurkinChar", "g");
        List<Tile> tilesG = queryC.query();

        for (Tile tile : tilesG) {
            tile.setGurkin(gherkinDao.queryForId(1));
            tiles1.add(tile);
        }
        QueryBuilder<Tile, Integer> queryP = tileDao.queryBuilder();
        queryP.where().eq("board_id", board1.getId()).and().eq("gurkinChar", "p");
        List<Tile> tilesP = queryC.query();

        for (Tile tile : tilesP) {
            tile.setGurkin(pickleDao.queryForId(1));
            tiles1.add(tile);
        }
        QueryBuilder<Tile, Integer> queryY = tileDao.queryBuilder();
        queryY.where().eq("board_id", board1.getId()).and().eq("gurkinChar", "y");
        List<Tile> tilesY = queryC.query();

        for (Tile tile : tilesY) {
            tile.setGurkin(yardlongDao.queryForId(1));
            tiles1.add(tile);
        }
        QueryBuilder<Tile, Integer> queryZ = tileDao.queryBuilder();
        queryZ.where().eq("board_id", board1.getId()).and().eq("gurkinChar", "z");
        List<Tile> tilesZ = queryC.query();

        for (Tile tile : tilesZ) {
            tile.setGurkin(zuchinniDao.queryForId(1));
            tiles1.add(tile);
        }
        board1.setTiles(tiles1);

        Board board2 = boardDao.queryForId(players.get(0).getGurkinBoard().getId());
        QueryBuilder<Tile, Integer> queryBuilder2 = tileDao.queryBuilder();
        queryBuilder2.where().eq("board_id", board2.getId()).and().isNull("gurkinID_id");
        Collection<Tile> tiles2 = queryBuilder2.query();

        QueryBuilder<Tile, Integer> queryC2 = tileDao.queryBuilder();
        queryC2.where().eq("board_id", board2.getId()).and().eq("gurkinChar", "c");
        List<Tile> tilesC2 = queryC.query();

        for (Tile tile : tilesC2) {
            tile.setGurkin(conichonDao.queryForId(2));
            tiles1.add(tile);
        }
        QueryBuilder<Tile, Integer> queryG2 = tileDao.queryBuilder();
        queryG2.where().eq("board_id", board2.getId()).and().eq("gurkinChar", "g");
        List<Tile> tilesG2 = queryC.query();

        for (Tile tile : tilesG2) {
            tile.setGurkin(gherkinDao.queryForId(2));
            tiles1.add(tile);
        }
        QueryBuilder<Tile, Integer> queryP2 = tileDao.queryBuilder();
        queryP2.where().eq("board_id", board2.getId()).and().eq("gurkinChar", "p");
        List<Tile> tilesP2 = queryC.query();

        for (Tile tile : tilesP2) {
            tile.setGurkin(pickleDao.queryForId(2));
            tiles1.add(tile);
        }
        QueryBuilder<Tile, Integer> queryY2 = tileDao.queryBuilder();
        queryY2.where().eq("board_id", board2.getId()).and().eq("gurkinChar", "y");
        List<Tile> tilesY2 = queryC.query();

        for (Tile tile : tilesY2) {
            tile.setGurkin(yardlongDao.queryForId(2));
            tiles1.add(tile);
        }
        QueryBuilder<Tile, Integer> queryZ2 = tileDao.queryBuilder();
        queryZ2.where().eq("board_id", board2.getId()).and().eq("gurkinChar", "z");
        List<Tile> tilesZ2 = queryC.query();

        for (Tile tile : tilesZ2) {
            tile.setGurkin(zuchinniDao.queryForId(2));
            tiles1.add(tile);
        }

        board2.setTiles(tiles2);


        // Set the retrieved board and tiles to the player object
        players.get(1).setGurkinBoard(board1);
        players.get(0).setGurkinBoard(board2);

        game.setPlayer1(players.get(1));
        game.setPlayer2(players.get(0));

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


        // Update player and its associated objects
        Player pl1 = game.getPlayer1();
        Player pl2 = game.getPlayer2();
        playerDao.update(pl1);
        playerDao.update(pl2);

        Board board1 = pl1.getGurkinBoard();
        boardDao.update(board1);

        Set<Conichon> conichon1 = new HashSet<Conichon>();
        Set<Gherkin> gherkin1 = new HashSet<Gherkin>();
        Set<Pickle> pickle1 = new HashSet<Pickle>();
        Set<Yardlong> yardlong1 = new HashSet<Yardlong>();
        Set<Zuchinni> zuchinni1 = new HashSet<Zuchinni>();

        for (Tile tile : board1.getTiles()) {
            if (tile.hasGurkin()) {
                if (tile.getGurkin() instanceof Conichon) {
                    conichon1.add((Conichon) tile.getGurkin());
                } else if (tile.getGurkin() instanceof Gherkin) {
                    gherkin1.add((Gherkin) tile.getGurkin());
                } else if (tile.getGurkin() instanceof Pickle) {
                    pickle1.add((Pickle) tile.getGurkin());
                } else if (tile.getGurkin() instanceof Yardlong) {
                    yardlong1.add((Yardlong) tile.getGurkin());
                } else if (tile.getGurkin() instanceof Zuchinni) {
                    zuchinni1.add((Zuchinni) tile.getGurkin());
                }

            }
        }

        for (Conichon conichon : conichon1) {
            conichonDao.update(conichon);
        }
        for (Gherkin gherkin : gherkin1) {
            gherkinDao.update(gherkin);
        }
        for (Pickle pickle : pickle1) {
            pickleDao.update(pickle);
        }
        for (Yardlong yardlong : yardlong1) {
            yardlongDao.update(yardlong);
        }
        for (Zuchinni zuchinni : zuchinni1) {
            zuchinniDao.update(zuchinni);
        }

        for (Tile tile : board1.getTiles()) {
            tileDao.update(tile);
        }

        Board board2 = pl2.getGurkinBoard();
        boardDao.update(board2);


        Set<Conichon> conichon2 = new HashSet<Conichon>();
        Set<Gherkin> gherkin2 = new HashSet<Gherkin>();
        Set<Pickle> pickle2 = new HashSet<Pickle>();
        Set<Yardlong> yardlong2 = new HashSet<Yardlong>();
        Set<Zuchinni> zuchinni2 = new HashSet<Zuchinni>();

        for (Tile tile : board2.getTiles()) {
            if (tile.hasGurkin()) {
                if (tile.getGurkin() instanceof Conichon) {
                    conichon2.add((Conichon) tile.getGurkin());
                } else if (tile.getGurkin() instanceof Gherkin) {
                    gherkin2.add((Gherkin) tile.getGurkin());
                } else if (tile.getGurkin() instanceof Pickle) {
                    pickle2.add((Pickle) tile.getGurkin());
                } else if (tile.getGurkin() instanceof Yardlong) {
                    yardlong2.add((Yardlong) tile.getGurkin());
                } else if (tile.getGurkin() instanceof Zuchinni) {
                    zuchinni2.add((Zuchinni) tile.getGurkin());
                }
            }
        }

        for (Conichon conichon : conichon2) {
            conichonDao.update(conichon);
        }
        for (Gherkin gherkin : gherkin2) {
            gherkinDao.update(gherkin);
        }
        for (Pickle pickle : pickle2) {
            pickleDao.update(pickle);
        }
        for (Yardlong yardlong : yardlong2) {
            yardlongDao.update(yardlong);
        }
        for (Zuchinni zuchinni : zuchinni2) {
            zuchinniDao.update(zuchinni);
        }

        for (Tile tile : board2.getTiles()) {
            tileDao.update(tile);
        }

        gameDao.update(game);
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

