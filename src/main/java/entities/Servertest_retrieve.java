package entities;

import Base.Player;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.util.List;

public class Servertest_retrieve {
    public static void main(String[] args) throws Exception {
        String databaseUrl = "jdbc:mysql://localhost:3306/jdbcdemo?serverTimezone=UTC";
        String username = "root";
        String password = "12345678";

        // create a connection source to our database
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl,username,password);

        Dao<Player, Integer> playerDao = DaoManager.createDao(connectionSource, Player.class);

        List<Player> players = playerDao.queryForAll();

        Player player1 = players.get(0);

        connectionSource.close();



    }
}
