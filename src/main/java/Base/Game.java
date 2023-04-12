package Base;

public class Game {
    Boolean multiplayer;
    Player player1;
    Player player2;

    public Game(Boolean multiplayer) {
        this.multiplayer = multiplayer;
        if (multiplayer) {
            player1 = new Player();
            player2 = new Player();
        } else {
            player1 = new Player();
            player2 = new AI();
        }
    }

    public Player getPlayer1() {
        return player1;
    }
    public Player getPlayer2() {
        return player2;
    }
    public Boolean getMultiplayer() {
        return multiplayer;
    }
}
