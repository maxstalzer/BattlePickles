package Controller;

import Base.Coordinates;
import Base.Direction;
import Base.Game;
import Base.Gurkins.*;
import Base.Players.AI;
import Base.Players.Player;
import Base.Turn;
import Gui.GameView;
import Gui.GridTile;
import Gui.GuiGurks;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

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
        game = new Game(true, this);
        game.addGameObserver(gameView);
        game.getPlayer1().setName(text);
        game.getPlayer2().setName(text1);
        gameView.initAttackViews();
        gameView.showPlacement(game.getPlayer1());
    }

    // Creates a new singleplayer game with the given difficulty
    public void startSingleplayerGame(String playerName, String difficulty) {
        game = new Game(false, this); // new singleplayer game
        game.addGameObserver(gameView); // add game observer to game
        game.getPlayer1().registerObserver(gameView.getContainer().getSidepanel());
        game.getPlayer1().setName(playerName);
        if (difficulty == "Easy") {
            game.getAIPlayer().setDifficulty(AI.Difficulty.Easy, game.getPlayer1());
        } else if (difficulty == "Medium") {
            game.getAIPlayer().setDifficulty(AI.Difficulty.Medium, game.getPlayer1());
        } else if (difficulty == "Hard") {
            game.getAIPlayer().setDifficulty(AI.Difficulty.Hard, game.getPlayer1());
        }
        game.getPlayer1().getGurkinBoard().registerBoardObserver(gameView.getContainer());
        gameView.initAttackViews();
        gameView.showPlacement(game.getPlayer1());

    }

    public Gurkin gurkTranslate (gurkinID gurkinID) {
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

    public gurkinID gurkTranslate (Gurkin gurkin) {
        if (gurkin instanceof Pickle) {
            return gurkinID.Pickle;
        } else if (gurkin instanceof Yardlong) {
            return gurkinID.Yardlong;
        } else if (gurkin instanceof Zuchinni) {
            return gurkinID.Zuchinni;
        } else if (gurkin instanceof Conichon) {
            return gurkinID.Conichon;
        } else {
            return gurkinID.Gherkin;
        }

    }

    public void placeGurkin(Coordinates startCors, Direction.direction direction, gurkinID gurkin) {
        game.getCurrentPlayer().validGurkinSetup(gurkTranslate(gurkin), direction, startCors);

    }
    public gurkinID getChosenGurk() {
        return gameView.getContainer().getSidepanel().getGurktypeField();
    }

    public Direction.direction getChosenDir() {return gameView.getContainer().getSidepanel().getDir();}

    public void redoPlacement() {
        game.getCurrentPlayer().resetPlacement();
        gameView.showPlacement(game.getCurrentPlayer());
    }

    public void endPlacement() {
        if (game.getMultiplayer()) {
            switch (Turn.getTurn()) {
                case "1":
                    Turn.changeTurn();
                    gameView.showPlacement(game.getPlayer2());
                    break;
                case "2":
                    Turn.changeTurn();
                    showGameplay();
            }
        } else {
            showGameplay();
        }
    }

    public void showGameplay() {
        game.getCurrentPlayer().getResultBoard().registerObserver(gameView.getCurrentAttackView(Turn.getTurn()));
        game.getCurrentPlayer().registerAttackObserver(gameView.getCurrentAttackView(Turn.getTurn()));
        gameView.showGameplay(Turn.getTurn(), game.getMultiplayer());
    }


    public void makeShot(Coordinates coordinates) {
        game.attack(coordinates);

    }

    public void changeTurnView() {
        showGameplay();
        if (Turn.getTurn().equals("2") && !game.getMultiplayer()) {
           Coordinates aishot = game.getAIPlayer().generateAttack();
           makeShot(aishot);
        }
    }







}