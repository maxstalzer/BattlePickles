package entities;

import Base.*;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Servertest {
    public static void main(String[] args) throws Exception {

        String databaseUrl = "jdbc:mysql://localhost:3306/jdbcdemo?serverTimezone=UTC";
        String username = "root";
        String password = "12345678";
        // create a connection source to our database
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl,username,password);

        TableUtils.createTableIfNotExists(connectionSource, Player.class);
        TableUtils.createTableIfNotExists(connectionSource, Board.class);
        TableUtils.createTableIfNotExists(connectionSource, Tile.class);
        TableUtils.createTableIfNotExists(connectionSource, Gurkin.class);

        Player p1 = new Player();
        p1.setName("An");
        Dao<Player, Integer> playerDao = DaoManager.createDao(connectionSource, Player.class);
        playerDao.create(p1);


        Dao<Board, Integer> boardDao = DaoManager.createDao(connectionSource, Board.class);
        boardDao.create(p1.getGurkinBoard());

        for(Tile tile : p1.getGurkinBoard().getTiles()) {
            tile.setBoard(p1.getGurkinBoard());
            tile.setGurkin(new Pickle());
            Dao<Gurkin, Integer> gurkinDao = DaoManager.createDao(connectionSource, Gurkin.class);
            gurkinDao.create(tile.getGurkin());
            gurkinDao.closeLastIterator();
            Dao<Tile, Integer> tileDao = DaoManager.createDao(connectionSource, Tile.class);
            tileDao.create(tile);
            tileDao.closeLastIterator();
            tile.setGurkin(new Pickle());
        }

        boardDao.closeLastIterator();
        playerDao.closeLastIterator();
        connectionSource.close();

        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl, databaseUsername, databasePassword);


    }
}
