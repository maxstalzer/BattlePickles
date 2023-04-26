package Online;
import Base.Players.Player;

import java.sql.SQLException;
import java.util.Scanner;


public class OnlineLoop {
    private OnlineGame game;
    private boolean gameOver;
    private Scanner scanner;

    public OnlineLoop() throws SQLException {
        OnlineGame game = new OnlineGame();
        scanner = new Scanner(System.in);
    }

    /*
    public void start() throws SQLException {
        while (!gameOver) {
            // Get the current player
            Player currentPlayer = game.getCurrentPlayer();

            // Wait for the current player to finish their turn
            waitForPlayerInput(currentPlayer);

            // Switch to the other player
            OnlineGame.switchPlayer();

            //write the actual player turn here

            // Check if the game is over
            if (game.GameOver()) {
                gameOver = true;
            }
        }

        // Print the winner
        Player winner = game.getWinner();
        System.out.println(winner.getName() + " wins!");
    }


    private void waitForPlayerInput(Player currentPlayer) throws SQLException {
        // Prompt the player to take their turn
        System.out.println(currentPlayer.getName() + ", it's your turn!");
        System.out.println("Press Enter when you're ready to make your move.");

        scanner.nextLine();

        // TODO: Do the player's turn actions here (shoot tile, etc.)

        // Update the player and game state in the database
        game.updateGameState();

        // Notify the other player that it's their turn
        Player otherPlayer = game.getOtherPlayer();
        System.out.println(otherPlayer.getName() + ", it's your turn now!");
    }

     */
}