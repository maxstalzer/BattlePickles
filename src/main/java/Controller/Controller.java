package Controller;

import Base.Coordinates;
import Base.Game;
import Base.Players.Player;
import javafx.scene.Scene;

import java.util.Observer;

public class Controller {
    private Coordinates coordinates;
    private Player player;

    private Game game;

    public Controller(Game game) {
        this.game = game.deepClone();
    }

    public void start() {

    }



}
