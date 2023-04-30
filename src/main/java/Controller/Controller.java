package Controller;

import Base.*;
import Base.AI;
import Base.ShotResults;
import Gui.GameView;
import Base.Database;

import java.util.List;

public class Controller {
    private Game game;

    private static Controller controllerInstance = null;
    public static Controller getControllerInstance(GameView gameView) {
        if (controllerInstance == null) {
            controllerInstance = new Controller(gameView);
        }
        return controllerInstance;
    }

    private GameView gameView;
    private Database database;


    public enum gurkinID {
        Pickle,
        Yardlong,
        Conichon,
        Zuchinni,
        Gherkin,
        Terrain
    }
    private Boolean terrain;

    private Controller(GameView gameView) {
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

    public void showLoadSavedGame() throws Exception {
            database = new Database();
            database.TestConnection("");
            gameView.showLoadSavedGame(database.getDatabases());
    }





    public void loadGame(String gameName) throws Exception {
        System.out.println("loaded");
        game = database.loadGame(gameName);
        // init views
        gameView.initPlacementViews(); //Initialising the placement views of the players
        gameView.initAttackViews(); // initialising the attack views of the players
        // Setting player names

        // Adding observers
        game.addGameObserver(gameView); // add game observer to game
        // register placement observers
        game.getPlayer1().getGurkinBoard().registerBoardObserver(gameView.getContainer1()); // register the placement container1 as an observer to player 1s board
        game.getPlayer2().getGurkinBoard().registerBoardObserver(gameView.getContainer2()); // register the placement container2 as an observer to player 2s board
        game.getPlayer1().registerObserver(gameView.getContainer1().getSidepanel()); // register the sidepanel as an observer to player 1
        game.getPlayer2().registerObserver(gameView.getContainer2().getSidepanel()); // register the sidepanel as an observer to player 2

        // register attack observers
        game.getPlayer1().getResultBoard().registerObserver(gameView.getP1AttackView()); // register shot board ovserver
        game.getPlayer1().registerAttackObserver(gameView.getP1AttackView()); // regiseter attackview observers
        game.getPlayer2().getResultBoard().registerObserver(gameView.getP2AttackView()); // register shot board ovserver
        game.getPlayer2().registerAttackObserver(gameView.getP2AttackView()); // regiseter attackview observers
        game.getPlayer1().getGurkinBoard().registerStatsObserver(gameView.getStatsPanel2());
        game.getPlayer2().getGurkinBoard().registerStatsObserver(gameView.getStatsPanel1());

        initLoadedGame();
    }

    public void initLoadedGame() {

        // init Attack views and placement views
        Turn.setTurn(game.getInitial_turn());
        game.getPlayer1().getGurkinBoard().prepareViewGurkin();
        game.getPlayer2().getGurkinBoard().prepareViewGurkin();
        prepareResultsView(game.getPlayer1().getResultBoard());
        prepareResultsView(game.getPlayer2().getResultBoard());
        showGameplay();
    }
    public void prepareResultsView(ShotResults resultsboard) {
        Character[][] shotBoard = resultsboard.getShotBoard();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j< 10; j++) {
                if (shotBoard[j][i] != null) {
                    if (shotBoard[j][i] == 'k') {
                        resultsboard.notifyKill(new Coordinates(j, i));
                    } else if (shotBoard[j][i] == 'x') {
                        resultsboard.notifyHit(new Coordinates(j, i));
                    } else if (shotBoard[j][i] == 'o') {
                        resultsboard.notifyMiss(new Coordinates(j, i));
                    }
                }
            }
        }
    }


    public void startLocalMultiplayerGame(String player1Name, String player2Name, Boolean terrain) { // Start a new local multiplayer game
        game = new Game(true); // new multiplayer game

        // init views
        gameView.initPlacementViews(); //Initialising the placement views of the players
        gameView.initAttackViews(); // initialising the attack views of the players
        // Setting player names
        game.getPlayer1().setName(player1Name);  // set the name of player 1
        game.getPlayer2().setName(player2Name); // set the name of player 2

        // Adding observers
        game.addGameObserver(gameView); // add game observer to game
        // register placement observers
        game.getPlayer1().getGurkinBoard().registerBoardObserver(gameView.getContainer1()); // register the placement container1 as an observer to player 1s board
        game.getPlayer2().getGurkinBoard().registerBoardObserver(gameView.getContainer2()); // register the placement container2 as an observer to player 2s board
        game.getPlayer1().registerObserver(gameView.getContainer1().getSidepanel()); // register the sidepanel as an observer to player 1
        game.getPlayer2().registerObserver(gameView.getContainer2().getSidepanel()); // register the sidepanel as an observer to player 2

        // register attack observers
        game.getPlayer1().getResultBoard().registerObserver(gameView.getP1AttackView()); // register shot board ovserver
        game.getPlayer1().registerAttackObserver(gameView.getP1AttackView()); // regiseter attackview observers
        game.getPlayer2().getResultBoard().registerObserver(gameView.getP2AttackView()); // register shot board ovserver
        game.getPlayer2().registerAttackObserver(gameView.getP2AttackView()); // regiseter attackview observers
        game.getPlayer1().getGurkinBoard().registerStatsObserver(gameView.getStatsPanel2());
        game.getPlayer2().getGurkinBoard().registerStatsObserver(gameView.getStatsPanel1());

        if (terrain) {
            this.terrain = true;
//            game.initTerrain();
        } else {
            this.terrain = false;
        }


        gameView.showConfirmPlace(Turn.getTurn(), game.getCurrentPlayer().getName()); // show the confirm placement scene
    }

    // Creates a new singleplayer game with the given difficulty
    public void startSingleplayerGame(String playerName, String difficulty, Boolean terrain) {
        game = new Game(false);// new singleplayer game
        game.getPlayer1().setName(playerName); // init player name

        gameView.initPlacementViews(); // init the placement views
        gameView.initAttackViews(); // init the attack views'

        // Adding observers
        game.addGameObserver(gameView); // add game observer to game
        // placement observers
        game.getPlayer1().registerObserver(gameView.getContainer1().getSidepanel()); // register the sidepanel as an observer to player 1
        game.getPlayer1().getGurkinBoard().registerBoardObserver(gameView.getContainer1()); // register the placement container1 as an observer to player 1s board

        //attack observers
        game.getPlayer1().getResultBoard().registerObserver(gameView.getP1AttackView()); // register shot board ovserver
        game.getPlayer1().registerAttackObserver(gameView.getP1AttackView()); // regiseter attackview observers
        game.getPlayer2().getResultBoard().registerObserver(gameView.getP2AttackView()); // register shot board ovserver
        game.getPlayer2().registerAttackObserver(gameView.getP2AttackView()); // regiseter attackview observers
        game.getPlayer1().getGurkinBoard().registerStatsObserver(gameView.getStatsPanel2());
        game.getPlayer2().getGurkinBoard().registerStatsObserver(gameView.getStatsPanel1());


        // Setting AI difficulty
        if (difficulty == "Easy") {
            game.getAIPlayer().setDifficulty(AI.Difficulty.Easy, game.getPlayer1());
        } else if (difficulty == "Medium") {
            game.getAIPlayer().setDifficulty(AI.Difficulty.Medium, game.getPlayer1());
        } else if (difficulty == "Hard") {
            game.getAIPlayer().setDifficulty(AI.Difficulty.Hard, game.getPlayer1());
        }
        if (terrain) {
            this.terrain = true;
        } else {
            this.terrain = false;
        }

        showPlacement(); // show the placement scene for player 1
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
        } else if (gurkinID.equals(Controller.gurkinID.Gherkin)) {
            return new Gherkin();
        } else {
            return new Terrain();
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
        } else if (gurkin instanceof Gherkin){
            return gurkinID.Gherkin;
        } else {
            return gurkinID.Terrain;
        }

    }

    public void placeGurkin(Coordinates startCors, Direction.direction direction, gurkinID gurkin) {
        game.getCurrentPlayer().validGurkinSetup(gurkTranslate(gurkin), direction, startCors);

    }
    public gurkinID getChosenGurk() {
        return gameView.getCurrentPlacementView(Turn.getTurn()).getSidepanel().getGurktypeField();
    }

    public Direction.direction getChosenDir() {
        if (Turn.getTurn().equals("1")) {
            return gameView.getContainer1().getSidepanel().getDir();
        }
        else {
            return gameView.getContainer2().getSidepanel().getDir();
        }
    }

    public void redoPlacement() {
        // resetting model and view
        game.getCurrentPlayer().resetPlacement();
        gameView.getCurrentPlacementView(Turn.getTurn()).resetPlacement();

        // add observers
        game.getCurrentPlayer().getGurkinBoard().registerBoardObserver(gameView.getCurrentPlacementView(Turn.getTurn())); // register the placement container1 as an observer to player 1s board

        gameView.showPlacement(Turn.getTurn(), game.getMultiplayer());
    }

    public void checkPlacement() {
        gameView.getCurrentPlacementView(Turn.getTurn()).showCheckPlacementPopup();

    }

    public void endPlacement() {
        if (game.getMultiplayer()) {
            switch (Turn.getTurn()) {
                case "1":
                    gameView.getContainer1().removeMouseClick();
                    Turn.changeTurn();
                    gameView.showConfirmPlace(Turn.getTurn(), game.getCurrentPlayer().getName());
                    break;
                case "2":
                    gameView.getContainer2().removeMouseClick();
                    Turn.changeTurn();
                    if (terrain) {
                        game.initTerrain();
                    }
                    gameView.confirmAttackView(Turn.getTurn(), game.getCurrentPlayer().getName());
            }
        } else {
            if (terrain) {
                game.initTerrain();
            }
            showGameplay();
        }
    }

    public void showGameplay() {
        gameView.showGameplay(Turn.getTurn(), game.getMultiplayer(), game.getCurrentPlayer(), game.getOpponent());
    }


    public void makeShot(Coordinates coordinates) {
        game.attack(coordinates);

    }

    public void changeTurnView() {
        if (Turn.getTurn().equals("2") && !game.getMultiplayer()) {
           Coordinates aishot = game.getAIPlayer().generateAttack();
           makeShot(aishot);
           showGameplay();
//            gameView.confirmAttackView(Turn.getTurn(), game.getCurrentPlayer().getName());
        } else if (game.getMultiplayer()) {
            gameView.confirmAttackView(Turn.getTurn(), game.getCurrentPlayer().getName());
        } else {
            showGameplay();
        }
    }


    public void saveGame() throws Exception {
        if (database == null) {
            gameView.showSaveGame("Enter name for game save");
        } else {
            database.updateGame(game);
            showMainMenu();
        }
    }

    public void saveNewGame(String gameName) throws Exception {
        database = new Database();
        List<String> gameNames = database.getDatabases();

        if (gameNames.contains(gameName)) {
            throw new Exception("Game name already exists");
        } else if (gameName.equals("")) {
            throw new Exception("Game name cannot be empty");
        } else if (gameName.contains(" ")) {
            throw new Exception("Game name cannot contain spaces");
        } else {
            database = new Database(gameName);
            database.saveGame(game);
        }

    }

    public void triggerEndTurn() {
        game.getCurrentPlayer().changeTurn();
    }

    public void displayKillGIFView(Gurkin gurk) {
        gameView.displaykillGIFView(gurk, game.getCurrentPlayer(), game.getOpponent());
    }

    public void showPlacement() {
        gameView.showPlacement(Turn.getTurn(), game.getMultiplayer());
    }

    public int[] getGurkinCount(int player) {
        if (player == 1) {
            return new int[]{game.getPlayer2().getRemaining_gurkins(), 5 - game.getPlayer1().getRemaining_gurkins()};
        } else {
            return new int[]{game.getPlayer1().getRemaining_gurkins(), 5 - game.getPlayer1().getRemaining_gurkins()};
        }
    }

    public Gurkin getGurkin(Coordinates cords) {
        return game.getOpponent().getGurkinBoard().getTile(cords).getGurkin();
    }

}
