/*
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

    private Player otherPlayer;

    private boolean gameOver = false;

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }
    public Player getOtherPlayer() {
        return this.otherPlayer;
    }

    public OnlineGame(String databaseName) throws SQLException {
        String databaseUrl = String.format("jdbc:mysql://172.20.10.3:3306/%s",databaseName);
        String databaseUsername = "sigurd";
        String databasePassword = "12345678";

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

        otherPlayer = players.get(1);
        otherPlayer.setCurrentPlayer(false);
        playerDao.update(currentPlayer);
    }

    public void switchPlayer() throws SQLException {
        List<Player> players = playerDao.queryForAll();
        for (Player player : players) {
            if (player.getId() != currentPlayer.getId()) {
                currentPlayer.setCurrentPlayer(false);
                otherPlayer = currentPlayer;
                playerDao.update(currentPlayer);
                player.setCurrentPlayer(true);
                playerDao.update(player);
                currentPlayer = player;
                break;
            }
        }
    }
    // Needs to be implemented further
    public void updateGameState() {}
    // Needs to be implemented further
    public Player getWinner() {
        return currentPlayer;
    }
    // Needs to be implemented further
    public boolean GameOver() {
        return this.gameOver;
    }
}
*/
