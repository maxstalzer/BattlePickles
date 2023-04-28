package Gui;

import Base.*;
import Base.Players.Player;
import Controller.Controller;
import Observers.GameObserver;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class GameView extends Application implements GameObserver {

    private Controller controller; // Controller
    private Stage primaryStage; // Primary stage

    private Scene placementScene1; // Placement scene for player1

    private Scene placementScene2; // Placement scene for player2

    private ShootingContainer shotContainer1;// Shot container for player 1

    private ShootingContainer shotContainer2; // Shot container for player 2

    private Scene attackScene1;// Attack scene for player1
    private Scene attackScene2; // Attack scene for player2
    private Scene waitScene; // Wait scene


    private Container container1; // container holding player1s placed gurkins
    private Container container2; // container holding player2s placed gurkins

    private MediaPlayer mainMenuMusic;
    private MediaPlayer finalSound;

    private double screenWidth;

    private double screenHeight;

    private Font joystix = Font.loadFont(getClass().getResourceAsStream("/joystix monospace.otf"), 24);
    private Font joystixTitle = Font.loadFont(getClass().getResourceAsStream("/joystix monospace.otf"), 50);
    private Font joystixSave = Font.loadFont(getClass().getResourceAsStream("/joystix monospace.otf"), 16);

    public Container getContainer1() { // returns container of player1
        return container1;
    }
    public Container getContainer2() { // returns container of player2
        return container2; }



    public GameView() { // Constructor
    }

    @Override
    public void start(Stage primaryStage) { // Start method
        this.controller = new Controller(this);
        this.primaryStage = primaryStage;
        this.mainMenuMusic = new MediaPlayer(new Media(new File("src/main/resources/menu.mp3").toURI().toString()));
        this.finalSound = new MediaPlayer(new Media(new File("src/main/resources/NothingToSeeHere.mp3").toURI().toString()));
        this.screenHeight = Screen.getPrimary().getBounds().getHeight();
        this.screenWidth = Screen.getPrimary().getBounds().getWidth();
        controller.showMainMenu(); // Show main menu

    }

    public void initPlacementViews() { // Initialize placement views
        this.container1 = new Container(controller);
        this.container2 = new Container(controller);
        this.placementScene1 = new Scene(container1, screenWidth, screenHeight);
        this.placementScene2 = new Scene(container2, screenWidth, screenHeight);
    }

    public void initAttackViews() { // Initialize attack views

        shotContainer1 = new ShootingContainer(controller);
        attackScene1 = new Scene(shotContainer1);
        shotContainer2 = new ShootingContainer(controller);
        attackScene2 = new Scene(shotContainer2);
    }



    public void setController(Controller controller) { // Setters
        this.controller = controller;

    }

    public Controller getController() {
        return controller;
    } // Getters


    public void startMainMenu() { // Start main menu

        BorderPane layout = new BorderPane();
        Scene scene = new Scene(layout, screenWidth, screenHeight);

        //Creating an image
        Image image = new Image("game_endcucucer.gif");

        //Setting the image view
        //ImageView imageView = new ImageView(image);

        Label label1 = new Label("BattlePickles ©");
        label1.setFont(joystixTitle);

        Button startButton = new Button("START");
        startButton.setFont(joystix);

        startButton.setOnAction(e -> showGameMode());

        VBox centerBox = new VBox(label1, startButton);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(20); // Add spacing between elements

        layout.setCenter(centerBox);
        BackgroundSize backgroundSize = new BackgroundSize(screenHeight, screenWidth, false, false, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        layout.setBackground(new Background(backgroundImage));

        primaryStage.setTitle("You don't know what you're getting yourself into");
        primaryStage.setScene(scene);
        mainMenuMusic.play();
        primaryStage.show();

    }

    public void showGameMode() { // Show game mode menu
        VBox layout = new VBox();

        Scene scene = new Scene(layout, screenWidth, screenHeight);
        Image image = new Image("game_endcucucer.gif");
        BackgroundSize backgroundSize = new BackgroundSize(screenHeight, screenWidth, false, false, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        layout.setBackground(new Background(backgroundImage));

        Label label2 = new Label("Game Select");
        label2.setFont(joystixTitle);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> controller.showMainMenu());
        backButton.setFont(joystix);

        Button AIButton = new Button("Singleplayer");
        AIButton.setOnAction(e -> controller.showSingleplayer());
        AIButton.setFont(joystix);

        Button multiplayerButton = new Button("Multiplayer");
        multiplayerButton.setOnAction(e -> controller.showMultiplayer());
        multiplayerButton.setFont(joystix);

        Button LoadSaved = new Button("Load saved game");
         LoadSaved.setOnAction(e -> {
             try {
                 controller.showLoadSavedGame();
             } catch (Exception ex) {
                 throw new RuntimeException(ex);
             }
         });
         LoadSaved.setFont(joystix);

        layout.getChildren().addAll(label2, AIButton, multiplayerButton, LoadSaved, backButton);
        layout.setAlignment(Pos.CENTER);

        primaryStage.setScene(scene);
    }

    public void showMultiplayer() { // Show multiplayer menu
        VBox layout = new VBox();
        Scene scene = new Scene(layout, screenWidth, screenHeight);
        Image image = new Image("game_endcucucer.gif");
        BackgroundSize backgroundSize = new BackgroundSize(screenHeight, screenWidth, false, false, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        layout.setBackground(new Background(backgroundImage));;

        Label label4 = new Label("Input player names");
        label4.setFont(joystixTitle);

        TextField p1NameField = new TextField();
        p1NameField.setFont(joystix);
        p1NameField.setMaxWidth(100);
        TextField p2NameField = new TextField();
        p2NameField.setFont(joystix);
        p2NameField.setMaxWidth(100);

        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> controller.startLocalMultiplayerGame(p1NameField.getText(), p2NameField.getText()));
        startButton.setFont(joystix);
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> controller.showGameMode());
        backButton.setFont(joystix);

        layout.getChildren().addAll(label4, p1NameField, p2NameField, startButton, backButton);
        layout.setAlignment(Pos.CENTER);

        primaryStage.setScene(scene);
    }

    public void showSingleplayer() { // Show singleplayer menu
        VBox layout = new VBox();
        Scene scene = new Scene(layout, screenWidth, screenHeight);
        Image image = new Image("game_endcucucer.gif");
        BackgroundSize backgroundSize = new BackgroundSize(screenHeight, screenWidth, false, false, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        layout.setBackground(new Background(backgroundImage));


        Label label3 = new Label("AI Difficulty");
        Label label5 = new Label("Player name");
        label3.setFont(joystix);
        label5.setFont(joystix);

        TextField p1NameField = new TextField();
        p1NameField.setMaxWidth(100);
        p1NameField.setFont(joystix);

        MenuButton menuButton = new MenuButton("");
        menuButton.getItems().addAll(new MenuItem("Easy"), new MenuItem("Medium"), new MenuItem("Hard"));
        menuButton.setText("Easy");
        menuButton.getItems().forEach(menuItem -> menuItem.setOnAction(event -> {
            menuButton.setText(menuItem.getText());
        }));
        menuButton.setFont(joystix);

        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> controller.startSingleplayerGame(p1NameField.getText(), menuButton.getText()));
        startButton.setFont(joystix);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> controller.showGameMode());
        backButton.setFont(joystix);

        layout.getChildren().addAll(label3, menuButton, label5, p1NameField, startButton, backButton);
        layout.setAlignment(Pos.CENTER);

        primaryStage.setScene(scene);
    }

    public void showPlacement(String turn, Boolean multiplayer) { // Show placement scene
        if (turn.equals("1")) { // Shows the placement scene for player 1

            primaryStage.setScene(placementScene1);
        } else if (turn.equals("2") && multiplayer) {
            primaryStage.setScene(placementScene2);
        }

    }

    public void showGameplay(String turn, Boolean multiplayer, Player player) { // Show gameplay scene
        HBox hbox;
        // Making the bottom stats panel
        Button saveButton = new Button("Save & Quit"); // Save and quit button
        saveButton.setOnAction(e -> {
            try {
                controller.saveGame();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        saveButton.setFont(joystixSave);

        Button noSaveButton = new Button("Quit without saving"); // Quit without saving button
        noSaveButton.setOnAction(e -> {
            try {
                controller.showMainMenu();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        noSaveButton.setFont(joystixSave);

        Label playerName = new Label();
        playerName.setText(player.getName());

        VBox panel = new VBox(10, playerName, saveButton, noSaveButton);
        panel.setAlignment(Pos.CENTER_RIGHT);
        panel.setPadding(new Insets(10));
        panel.setMaxWidth(400);
        panel.setMaxHeight(screenHeight);

        if (turn.equals("1") ) {
            container1.hideSidePanel();

            container1.setScaleX(0.6);
            container1.setScaleY(0.6);

            shotContainer1.setScaleY(0.6);
            shotContainer1.setScaleX(0.6);


            hbox = new HBox(container1, shotContainer1);
            hbox.setMaxWidth(screenWidth - 400);
            hbox.setMaxHeight(screenHeight);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setSpacing(-500);
            hbox.setStyle("-fx-background-color: transparent;");
            hbox.setBackground(Background.EMPTY);


            HBox outerBox = new HBox(hbox, panel);
            outerBox.setMaxWidth(screenWidth);
            outerBox.setMaxHeight(screenHeight);
            outerBox.setSpacing(40);



            Image image = new Image("Brine copy.gif");
            BackgroundSize backgroundSize = new BackgroundSize(screenHeight, screenWidth, false, false, false, true);
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            outerBox.setBackground(new Background(backgroundImage));
            attackScene1 = new Scene(outerBox, screenWidth, screenHeight);
            primaryStage.setScene(attackScene1);
        } else if (turn.equals("2") && multiplayer) {
            container2.hideSidePanel();

            container2.setScaleX(0.6);
            container2.setScaleY(0.6);

            shotContainer2.setScaleY(0.6);
            shotContainer2.setScaleX(0.6);
            hbox = new HBox( container2, shotContainer2);
            hbox.setPadding(new Insets(10));

            VBox vbox = new VBox(hbox, panel);
            vbox.setSpacing(10);
            vbox.setPadding(new Insets(10));


            attackScene2 = new Scene(vbox, screenWidth, screenHeight);
            primaryStage.setScene(attackScene2);
        }
    }

    public Container getCurrentPlacementView(String turn) {
        if (turn.equals("1")) {
            return container1;
        } else {
            return container2;
        }
    }

    public ShootingContainer getCurrentAttackView(String turn) {
        if (turn.equals("1")) {
            return shotContainer1;
        } else {
            return shotContainer2;
        }
    }
    public ShootingContainer getP1AttackView() {
        return shotContainer1;
    }

    public ShootingContainer getP2AttackView() {
        return shotContainer2;
    }

    public void showWait() {
        primaryStage.setScene(waitScene);
    }

    public void showWinner(Player winner) {
        mainMenuMusic.stop();
        VBox layout = new VBox();
        Scene scene = new Scene(layout, screenWidth, screenHeight);
        BackgroundImage image = new BackgroundImage(new Image("Brine copy.gif"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, new BackgroundSize(layout.getWidth(), layout.getHeight(), false, false, false, false));
        layout.setBackground(new Background(image));

        finalSound.play();

        Label label1 = new Label("Winner is " + winner.getName());
        label1.setFont(joystix);
        label1.setEffect(new DropShadow());

        Button backButton = new Button("Go to main menu");
        backButton.setOnAction(e -> {
            finalSound.stop();
            controller.showMainMenu();
        });
        backButton.setFont(joystix);

        layout.getChildren().addAll(label1, backButton);
        layout.setAlignment(Pos.CENTER);


        primaryStage.setScene(scene);
    }

    public void showLoadSavedGame(List<String> databaseNames) {
        VBox layout = new VBox();
        Scene scene = new Scene(layout, screenWidth, screenHeight);

        Label label3 = new Label("Select Game to load");
        label3.setFont(joystix);

        MenuButton menuButton = new MenuButton("");
        // Add all the names of the games in the database to the menu using a for loop
        for (String gameName : databaseNames) {
            MenuItem menuItem = new MenuItem(gameName);
            menuButton.getItems().add(menuItem);
        }

        menuButton.setText("Choose Game Save");

        menuButton.getItems().forEach(menuItem -> menuItem.setOnAction(event -> {
            menuButton.setText(menuItem.getText());
        }));
        menuButton.setFont(joystix);

        Button startButton = new Button("Load Game");
        startButton.setOnAction(e -> {
            try {
                controller.loadGame(menuButton.getText());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        startButton.setFont(joystix);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> controller.showMainMenu());
        backButton.setFont(joystix);

        layout.getChildren().addAll(label3, menuButton, startButton, backButton);
        layout.setAlignment(Pos.CENTER);

        primaryStage.setScene(scene);
    }

    public void showSaveGame(String exceptionMessage) {
        VBox layout = new VBox();
        Scene scene = new Scene(layout, screenWidth, screenHeight);

        Label label3 = new Label(exceptionMessage);

        TextField gameNameField = new TextField();
        gameNameField.setMaxWidth(100);
        gameNameField.setFont(joystix);

        Button startButton = new Button("Save Game");
        startButton.setOnAction(e -> {
            try {
                controller.saveNewGame(gameNameField.getText());
                controller.showMainMenu();
            } catch (Exception ex) {
                String exMessage = ex.getMessage();
                showSaveGame(exMessage);
            }
        });
        startButton.setFont(joystix);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> controller.showGameplay());
        backButton.setFont(joystix);

        layout.getChildren().addAll(label3, gameNameField, startButton, backButton);
        layout.setAlignment(Pos.CENTER);

        primaryStage.setScene(scene);

    }
}


