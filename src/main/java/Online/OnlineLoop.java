package Online;
import Base.Players.Player;

import java.sql.SQLException;
import java.util.Scanner;


//this is basically the controller, will be transferred to the controller once we know what the fuck we're supposed to do
public class OnlineLoop {
    private OnlineGame game;
    private Scanner scanner;

    public OnlineLoop(String databaseName) throws SQLException {
        OnlineGame game = new OnlineGame(databaseName);
        scanner = new Scanner(System.in);
    }

    /*
    public void start() throws SQLException {
        while (!game.GameOver()) {
            // Get the current player
            Player currentPlayer = game.getCurrentPlayer();

            // Wait for the current player to finish their turn
            waitForPlayerInput(currentPlayer);

            // Switch to the other player
            game.switchPlayer();

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
        // updateGameState needs to be implemented further
        game.updateGameState();

        // Notify the other player that it's their turn
        Player otherPlayer = game.getOtherPlayer();
        System.out.println(otherPlayer.getName() + ", it's your turn now!");
    }
}

     *//*

}*/
