package Controller;

import Base.Coordinates;
import Base.Direction;
import Base.Game;
import Base.Gurkins.*;
import Base.Players.AI;
import Gui.GameView;
import Gui.GridTile;
import Gui.GuiGurks;
import javafx.stage.Stage;

import static Base.Direction.direction.Horizontal;
import static Base.Direction.direction.Vertical;

public class Controller {
    private Game game;
    private GameView gameView;

    private Stage primaryStage;

    public Gurkin Gurktype;

    public enum gurkinID {
        Pickle,
        Yardlong,
        Conichon,
        Zuchinni,
        Gherkin
    }

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

    public void startLocalMultiplayerGame(String text, String text1) {
        game = new Game(true);
        game.getPlayer1().setName(text);
        game.getPlayer2().setName(text1);
        gameView.showPlacement(game.getPlayer1());
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

    private Gurkin gurkTranslate (gurkinID gurkinID) {
        if (gurkinID.equals(Controller.gurkinID.Pickle)) {
            return new Pickle();
        } else if (gurkinID.equals(Controller.gurkinID.Yardlong)) {
            return new Yardlong();
        } else if (gurkinID.equals(Controller.gurkinID.Zuchinni)) {
            return new Zuchinni();
        } else if (gurkinID.equals(Controller.gurkinID.Conichon)) {
            return new Conichon();
        } else {
            return new Gherkin();
        }

    }

    public void placeGurkin(Coordinates startCors, Direction.direction direction, gurkinID gurkin) {
        game.getCurrentPlayer().validGurkinSetup(gurkTranslate(gurkin), direction, startCors);


        gameView.getContainer().placeGurkin(startCors,direction,gurkin);

    }



}
