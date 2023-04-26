package Online;
import java.sql.SQLException;
import java.util.List;
import Base.Players.Player;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

public class OnlineGame {
    private Dao<Player, Integer> playerDao;
    private Dao<GameState, Integer> gameStateDao;
    private Player currentPlayer;

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public OnlineGame() throws SQLException {
        String databaseUrl = "jdbc:mysql://localhost:3306/game_db";
        String databaseUsername = "username";
        String databasePassword = "password";

        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl, databaseUsername, databasePassword);

        playerDao = DaoManager.createDao(connectionSource, Player.class);
        gameStateDao = DaoManager.createDao(connectionSource, GameState.class);

        // Create two players if the table is empty
        if (playerDao.queryForAll().size() == 0) {
            Player player1 = new Player(0, "Player 1", true);
            Player player2 = new Player(0, "Player 2", false);
            playerDao.create(player1);
            playerDao.create(player2);
        }

        // Set the first player as the current player
        List<Player> players = playerDao.queryForAll();
        currentPlayer = players.get(0);
        currentPlayer.setCurrentPlayer(true);
        playerDao.update(currentPlayer);
    }

    public void switchPlayer() throws SQLException {
        List<Player> players = playerDao.queryForAll();
        for (Player player : players) {
            if (player.getId() != currentPlayer.getId()) {
                currentPlayer.setCurrentPlayer(false);
                playerDao.update(currentPlayer);
                player.setCurrentPlayer(true);
                playerDao.update(player);
                currentPlayer = player;
                break;
            }
        }
    }
}
