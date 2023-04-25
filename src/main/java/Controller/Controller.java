package Controller;

import Base.Coordinates;
import Base.Direction;
import Base.Game;
import Base.Gurkins.Zuchinni;
import Base.Players.AI;
import Gui.GameView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import static Base.Direction.direction.Horizontal;

public class Controller implements ActionListener {
    private Game game;
    private GameView gameView;

    private Stage primaryStage;

    public Controller(GameView gameView) {
        this.gameView = gameView;
    }


    public void showMainMenu() {
        gameView.startMainMenu();
    }

    public void showSingleplayer() {
        gameView.showSingleplayer();
    }

    public void showMultiplayer() {
        gameView.showMultiplayer();
    }

    public void showGameMode() {
        gameView.showGameMode();
    }

    public void startMultiplayerGame(String text, String text1) {

    }

    // Creates a new singleplayer game with the given difficulty
    public void startSingleplayerGame(String playerName, String difficulty) {
        game = new Game(false);
        game.getPlayer1().setName(playerName);
        if (difficulty == "Easy") {
            game.getAIPlayer().setDifficulty(AI.Difficulty.Easy, game.getPlayer1());
        } else if (difficulty == "Medium") {
            game.getAIPlayer().setDifficulty(AI.Difficulty.Medium, game.getPlayer1());
        } else if (difficulty == "Hard") {
            game.getAIPlayer().setDifficulty(AI.Difficulty.Hard, game.getPlayer1());
        }
        gameView.showPlacement(game.getPlayer1());
    }

    public void placeGurksSingleplayer() {
        Coordinates coords = gameView.getContainer().getPosition();
        game.getGurkinBoard().placeGurkin(new Zuchinni(), Horizontal, coords);
    }
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        System.out.println("jaiksia");
    }
}
