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
                    System.out.println("------------Assign Pickle: " + oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin());
                    pickle1.setBoard(board1);
                    pickle1.setLives(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getLives());
                    pickle1.setId(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getId());
                    pickle1.setSize(oldgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(i,j)).getGurkin().getSize());
                    pickle1.addTile(board1.getTile(new Coordinates(i,j)));
                    board1.getTile(new Coordinates(i,j)).setGurkin(pickle1);
                    System.out.println("-------------TileAssign: " + board1.getTile(new Coordinates(1,1)).getGurkin().getLives());
                    System.out.println("-------------TileAssign: " + board1.getTile(new Coordinates(1,1)).getGurkin().getLives());

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
        System.out.print("-------------InPlayer: " + player1.getGurkinBoard().getTile(new Coordinates(1,1)).getGurkin().getLives() + "-------");

        newgame.setPlayer1(player1);

        System.out.print("-------------InGame: " + newgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(1,1)).getGurkin().getLives() + "-------");

        // SET PLAYER 2
        Player player2 = new Player(oldgame.getId(),oldgame.getPlayer2().getName(), oldgame.getPlayer1().getCurrentPlayer());
        player2.setRemaining_gurkins(oldgame.getPlayer2().getRemaining_gurkins());

        System.out.print("-------------InGame1: " + newgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(1,1)).getGurkin().getLives() + "-------");

        Board board2 = new Board();
        board2.setId(oldgame.getPlayer2().getGurkinBoard().getId());
        board2.setConichon(oldgame.getPlayer2().getGurkinBoard().getConichon());
        board2.setGherkin(oldgame.getPlayer2().getGurkinBoard().getGherkin());
        board2.setPickle(oldgame.getPlayer2().getGurkinBoard().getPickle());
        board2.setYardlong(oldgame.getPlayer2().getGurkinBoard().getYardlong());
        board2.setZuchinni(oldgame.getPlayer2().getGurkinBoard().getZuchinni());

        System.out.print("-------------InGame2: " + newgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(1,1)).getGurkin().getLives() + "-------");

        Gurkin conichon2 = new Conichon();
        Gurkin gherkin2 = new Gherkin();
        Gurkin pickle2 = new Pickle();
        Gurkin yardlong2 = new Yardlong();
        Gurkin zuchinni2 = new Zuchinni();

        System.out.print("-------------InGame3: " + newgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(1,1)).getGurkin().getLives() + "-------");

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

        System.out.print("-------------InGame4: " + newgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(1,1)).getGurkin().getLives() + "-------");
        player2.setGurkinBoard(board2);

        System.out.print("-------------InGame5: " + newgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(1,1)).getGurkin().getLives() + "-------");
        newgame.setPlayer2(player2);

        System.out.print("-------------InGame6: " + newgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(1,1)).getGurkin().getLives() + "-------");

        connectionSource.close();
        System.out.print("-------------DatabaseHasLives: " + newgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(1,1)).getGurkin().getLives() + "-------");
        System.out.println("---------------DatabasePickle: " + newgame.getPlayer1().getGurkinBoard().getTile(new Coordinates(1,1)).getPickle());
        return newgame;

        /*
        // Retrieve all players associated with the game from the database



        // Retrieve all boards associated with each player from the database


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


        game.setPlayer1(players.get(1));
        game.setPlayer2(players.get(0));



        connectionSource.close();


        return game;*/
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

        /*
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

         */

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

        /*
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

         */
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

    public Board getBoard() throws Exception {
        JdbcConnectionSource connectionSource = new JdbcConnectionSource(databaseURL, username, password);

        Dao<Board, Integer> boardDao = DaoManager.createDao(connectionSource, Board.class);
        Dao<Tile, Integer> tileDao = DaoManager.createDao(connectionSource, Tile.class);
        Dao<Conichon, Integer> conichonDao = DaoManager.createDao(connectionSource, Conichon.class);
        Dao<Gherkin, Integer> gherkinDao = DaoManager.createDao(connectionSource, Gherkin.class);
        Dao<Pickle, Integer> pickleDao = DaoManager.createDao(connectionSource, Pickle.class);
        Dao<Yardlong, Integer> yardlongDao = DaoManager.createDao(connectionSource, Yardlong.class);
        Dao<Zuchinni, Integer> zuchinniDao = DaoManager.createDao(connectionSource, Zuchinni.class);

        Board board = boardDao.queryForId(1);

        // Access the Tile objects associated with the Board
        Collection<Tile> tiles = tileDao.queryForEq("board_id", board.getId());
        System.out.println("-------------" + tiles.size() + "----------------");
        // Iterate over the Tile objects and perform any necessary operations
        for (Tile tile : tiles) {
            // Do something with each Tile object
            System.out.println("X: " + tile.getX());
            System.out.println("Y: " + tile.getY());
        }
        /*
        if (!(board.getConichon() == null)) {
            Conichon conichon1 = conichonDao.queryForId(board.getConichon().getId());
            board.setConichon(conichon1);
        }
        if (!(board.getGherkin() == null)) {
            Gherkin gherkin1 = gherkinDao.queryForId(board.getGherkin().getId());
            board.setGherkin(gherkin1);
        }
        if (!(board.getPickle() == null)) {
            Pickle pickle1 = pickleDao.queryForId(board.getPickle().getId());
            board.setPickle(pickle1);
        }
        if (!(board.getYardlong() == null)) {
            Yardlong yardlong1 = yardlongDao.queryForId(board.getYardlong().getId());
            board.setYardlong(yardlong1);
        }
        if (!(board.getZuchinni() == null)) {
            Zuchinni zuchinni1 = zuchinniDao.queryForId(board.getZuchinni().getId());
            board.setZuchinni(zuchinni1);
        }*/

        System.out.println("For board: ---------- Pickle lives ------: " + board.getPickle().getLives() + "------");
        for (Tile tile : tiles) {
            if (tile.hasGurkin()) {
                System.out.println("-----Pickle lives-----:" + tile.getPickle().getLives() + "---------");
            }
        }

        board.setTiles(tiles);

        // Close the connection source and Dao when finished
        boardDao.closeLastIterator();
        connectionSource.close();
        return board;
    }

}

