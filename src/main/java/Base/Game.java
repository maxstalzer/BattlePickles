package Base;

import Base.Players.AI;
import Base.Players.Player;

public class Game {
    Boolean multiplayer;
    Player player1;
    Player player2;
    Boolean game_Over;
    private String initial_turn;

    public Game(Boolean multiplayer) {
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
    }

    public Player getPlayer1() {
        return player1;
    }
    public Player getPlayer2() {
        return player2;
    }
    public AI getAIPlayer() {
        if (!multiplayer) {
            return (AI) player2;
        } else return null;
    }
    public Boolean getMultiplayer() {
        return multiplayer;
    }

    public boolean changePlacement(Player player) {
        return (player.getRemaining_gurkins() == 5);
    }

    public Game deepClone() {
        Game copy = new Game(multiplayer);
        copy.player1 = player1.deepClone();
        copy.player2 = player2.deepClone();
        copy.game_Over = game_Over;
        copy.initial_turn = Turn.getTurn();
        return copy;
    }
}
