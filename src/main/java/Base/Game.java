package Base;

import Base.Players.AI;
import Base.Players.Player;

public class Game {
    Boolean multiplayer; // true if multiplayer, false if singleplayer
    Player player1; // Player 1
    Player player2; // Player 2
    Boolean game_Over; // true if game is over

    private String gameID; // Game ID
    private String initial_turn; // Initial turn

    public Game(Boolean multiplayer) { // Constructor
        this.multiplayer = multiplayer;
        Turn.init_turn();
        if (multiplayer) {
            player1 = new Player();
            player2 = new Player();
        } else {
            player1 = new Player();
            player2 = new AI();
        }
        this.game_Over = false;

        int gameID = (int) (Math.random() * 1000000); // Generate random game ID
        this.gameID = Integer.toString(gameID);
    }

    public Player getPlayer1() {
        return player1;
    } // Getters
    public Player getPlayer2() {
        return player2;
    } // Getters
    public AI getAIPlayer() { // Returns AI player if singleplayer, null if multiplayer
        if (!multiplayer) {
            return (AI) player2;
        } else return null;
    }
    public Boolean getMultiplayer() {
        return multiplayer;
    } // Getters

    public boolean changePlacement(Player player) {
        return (player.getRemaining_gurkins() == 5);
    } // Check if player has finished placing gurkins

    public Game deepClone() {   // Deep clone of game
        Game copy = new Game(multiplayer);
        copy.player1 = player1.deepClone();
        copy.player2 = player2.deepClone();
        copy.game_Over = game_Over;
        copy.initial_turn = Turn.getTurn();
        return copy;
    }

    public void loadGame(String gameID) { // Load game from file
//        functionality to come

    }

    public Board getGurkinBoard() {
        return player1.getGurkinBoard();
    }
}
