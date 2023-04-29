package Base;

import Base.Players.AI;
import Base.Players.Player;
import Controller.Controller;
import Observers.GameObserver;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.HashSet;
import java.util.Set;

@DatabaseTable(tableName = "Game")
public class Game implements GameObserver {
    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "multiplayer")
    Boolean multiplayer; // true if multiplayer, false if singleplayer

    /* @ForeignCollectionField(columnName = "Players", eager = true)
    private Collection<Player> players = new ArrayList<>(); */

    @DatabaseField(columnName = "player1_id", foreign = true,foreignAutoRefresh = true)
    Player player1; // Player 1
    @DatabaseField(columnName = "player2_id", foreign = true,foreignAutoRefresh = true)
    Player player2; // Player 2
    @DatabaseField(columnName = "game_over")
    Boolean game_Over; // true if game is over

    @DatabaseField(columnName = "game_id")
    private String gameID; // Game ID
    @DatabaseField(columnName = "initial_turn")
    private String initial_turn; // Initial// turn

    private Set<GameObserver> gameObserverSet = new HashSet<GameObserver>();




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

    public boolean checkPlacement(Player player) {
        return (player.getRemaining_gurkins() == 5);
    } // Check if player has finished placing gurkins

//    public Game deepClone() {   // Deep clone of game
//        Game copy = new Game(multiplayer, controller);
//        copy.player1 = player1.deepClone();
//        copy.player2 = player2.deepClone();
//        copy.game_Over = game_Over;
//        copy.initial_turn = Turn.getTurn();
//        return copy;
//    }

    public void loadGame(String gameID) { // Load game from file
//        functionality to come

    }

    public Board getGurkinBoard() {
        return player1.getGurkinBoard();
    }

    public Player getCurrentPlayer() {
        if (Turn.getTurn().equals("1")) {
            return player1;
        }
        return player2;
    }

    public void attack(Coordinates coords) {
        if (Turn.getTurn().equals("1")) {
            player1.shoot(player2.getGurkinBoard(), coords);

        } else {
            player2.shoot(player1.getGurkinBoard(), coords);
        }
        checkWin();
    }

    public Player getOpponent() {
        System.out.println(Turn.getTurn());
        if (Turn.getTurn().equals("1")) {
            return player2;
        }
        return player1;
    }

    public void checkWin() {
        if (player1.checkWin()) {
            showWinner(player1);
        } else if (player2.checkWin()) {
            showWinner(player2);
        }
    }

    public void showWinner(Player player) {
        game_Over = true;
        for (GameObserver gameObserver : gameObserverSet) {
            gameObserver.showWinner(player);
        }
    }

    public void addGameObserver(GameObserver gameObserver) {
        gameObserverSet.add(gameObserver);
    }

    public int getId() {
        return id;
    }

    public Game() {

    }

    public void setInitial_turn() {
        initial_turn = Turn.getTurn();
    }

    public void initLoadedGame() {
        player1.getGurkinBoard().prepareViewGurkin();
        player2.getGurkinBoard().prepareViewGurkin();
        player1.getResultBoard().prepareResultsView();
        player2.getResultBoard().prepareResultsView();

        Turn.setTurn(initial_turn);

    }

    public void initTerrain() {
        player1.getGurkinBoard().initTerrain();
        player2.getGurkinBoard().initTerrain();
    }

}
