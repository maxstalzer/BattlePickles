/*
import Base.Players.Player;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.Connection;
import java.sql.DriverManager;

public class Servertest {
    public static void main(String[] args) throws Exception {

        //String driver = "com.mysql.cj.jdbc.Driver";
        //Class.forName(driver);

        String databaseUrl = "jdbc:mysql://localhost:3306/?user=root";

        // create a connection source to our database
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl,"root","_AndersIsASmellyBoii_");


        //Connection dbConnection = DriverManager.getConnection(databaseUrl, "root","12345678");

        // instantiate the dao
        Dao<Player, String> userDao = DaoManager.createDao(connectionSource, Player.class);

        // if you need to create the 'accounts' table make this call
        //TableUtils.createTable(connectionSource, Player.class);
        //Once we have configured our database objects, we can use them to persist an Account to the database and query for it from the database by its ID:


        // create an instance of Account
        Player p1 = new Player();
        p1.setName("Gurd");
        //p1.setupBoard();

        // persist the account object to the database
        userDao.create(p1);}
        */
/*
        // retrieve the account from the database by its id field (name)
        User user2 = userDao.queryForId("Jim Coakley");
        System.out.println("Account: " + user2.getName());

        // close the connection source
        connectionSource.close();
    }*//*

}

*/
