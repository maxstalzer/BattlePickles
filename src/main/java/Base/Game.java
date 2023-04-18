package Base;

public class Game {
    Boolean multiplayer;
    Player player1;
    Player player2;
    Boolean game_Over;

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


}
