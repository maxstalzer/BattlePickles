package Gui;

import Base.*;
import Base.Gurkins.*;
import Base.Players.Player;
import Controller.Controller;
import Observers.GameObserver;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import java.util.concurrent.*;

public class GameView extends Application implements GameObserver {

    private Controller controller; // Controller
    private Stage primaryStage; // Primary stage

    private Scene placementScene1; // Placement scene for player1

    private Scene placementScene2; // Placement scene for player2

    private ShootingContainer shotContainer1;// Shot container for player 1

    private ShootingContainer shotContainer2; // Shot container for player 2

    private Scene attackScene1;// Attack scene for player1
    private Scene attackScene2; // Attack scene for player2

    private StatsPanel statsPanel1; // Stats panel for player1
    private StatsPanel statsPanel2; // Stats panel for player2

    private Scene waitScene; // Wait scene

    private MediaPlayer gifPlayer; // Gif player
    private Container container1; // container holding player1s placed gurkins
    private Container container2; // container holding player2s placed gurkins

    private MediaPlayer mainMenuMusic;
    private MediaPlayer finalSound;
    private MediaPlayer buttonClick;
    private double screenWidth;
    private double screenHeight;



    private Font joystix = Font.loadFont(getClass().getResourceAsStream("/joystix monospace.otf"), 24);
    private Font joystixTitle = Font.loadFont(getClass().getResourceAsStream("/joystix monospace.otf"), 50);
    private Font joystixSave = Font.loadFont(getClass().getResourceAsStream("/joystix monospace.otf"), 16);

    private Boolean isTerrainEnabled;


    public Container getContainer1() { // returns container of player1
        return container1;
    }
    public Container getContainer2() { // returns container of player2
        return container2; }



    public GameView() {
        // Constructor
    }

    @Override
    public void start(Stage primaryStage) { // Start method
        this.controller = Controller.getControllerInstance(this);
        this.primaryStage = primaryStage;
        this.mainMenuMusic = new MediaPlayer(new Media(new File("src/main/resources/menu.mp3").toURI().toString()));
        this.finalSound = new MediaPlayer(new Media(new File("src/main/resources/NothingToSeeHere.mp3").toURI().toString()));
        this.screenHeight = 956;
        this.screenWidth = 1470;
        Font.loadFont(getClass().getResource("/joystix monospace.otf").toExternalForm(), 10);
        controller.showMainMenu(); // Show main menu

    }

    public void initPlacementViews() { // Initialize placement views
        this.container1 = new Container(controller);
        this.container2 = new Container(controller);
        this.placementScene1 = new Scene(container1, screenWidth, screenHeight);
        this.placementScene2 = new Scene(container2, screenWidth, screenHeight);
    }

    public void initAttackViews() { // Initialize attack views

        shotContainer1 = new ShootingContainer(controller); // container holding player1s shots
        attackScene1 = new Scene(shotContainer1); // scene holding player1s shots
        shotContainer2 = new ShootingContainer(controller); // container holding player2s shots
        attackScene2 = new Scene(shotContainer2); // scene holding player2s shots

        statsPanel1 = new StatsPanel(controller, 1); // stats panel for player1
        statsPanel2 = new StatsPanel(controller, 2); // stats panel for player2

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

        startButton.setOnAction(e -> {
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            showGameMode();

        }
        );

        VBox centerBox = new VBox(label1, startButton);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(20); // Add spacing between elements

        layout.setCenter(centerBox);
        BackgroundSize backgroundSize = new BackgroundSize(screenHeight, screenWidth, false, false, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        layout.setBackground(new Background(backgroundImage));

        primaryStage.setTitle("You don't know what you're getting yourself into");
        primaryStage.setScene(scene);
        mainMenuMusic.setCycleCount(MediaPlayer.INDEFINITE);
        mainMenuMusic.setVolume(0.2);
        mainMenuMusic.play();
        primaryStage.show();

    }

    public void showGameMode() { // Show game mode menu
        VBox layout = new VBox(10);

        Scene scene = new Scene(layout, screenWidth, screenHeight);
        Image image = new Image("game_endcucucer.gif");
        BackgroundSize backgroundSize = new BackgroundSize(screenHeight, screenWidth, false, false, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        layout.setBackground(new Background(backgroundImage));

        Label label2 = new Label("Game Select");
        label2.setFont(joystixTitle);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            controller.showMainMenu();
        });
        backButton.setFont(joystix);

        Button AIButton = new Button("Singleplayer");
        AIButton.setOnAction(e -> {
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            controller.showSingleplayer();
        });
        AIButton.setFont(joystix);

        Button multiplayerButton = new Button("Multiplayer");
        multiplayerButton.setOnAction(e -> {
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            controller.showMultiplayer();
        });
        multiplayerButton.setFont(joystix);

        Button LoadSaved = new Button("Load saved game");
        LoadSaved.setOnAction(e -> {
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<?> future = executor.submit(() -> {
                try {
                    controller.showLoadSavedGame();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
            executor.shutdown();
            try {
                future.get(5, TimeUnit.SECONDS); // wait for 5 seconds
            } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                future.cancel(true); // interrupt the task if it takes longer than 5 seconds
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Can not load saved game");
                alert.setContentText("The operation took too long to complete.");
                alert.showAndWait();
            }
        });
         LoadSaved.setFont(joystix);

        layout.getChildren().addAll(label2, AIButton, multiplayerButton, LoadSaved, backButton);
        layout.setAlignment(Pos.CENTER);

        primaryStage.setScene(scene);
    }

    public void showMultiplayer() { // Show multiplayer menu
        VBox layout = new VBox(10);
        Scene scene = new Scene(layout, screenWidth, screenHeight);
        Image image = new Image("game_endcucucer.gif");
        BackgroundSize backgroundSize = new BackgroundSize(screenHeight, screenWidth, false, false, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        layout.setBackground(new Background(backgroundImage));;

        Label label4 = new Label("Input player names");
        label4.setFont(joystixTitle);

        TextField p1NameField = new TextField();
        p1NameField.setText("Player 1");
        p1NameField.setFont(joystix);
        p1NameField.setMaxWidth(200);
        p1NameField.setOnMouseClicked(e -> {
            if (p1NameField.getText().equals("Player 1")) {
                p1NameField.setText("");
            }
        });
        p1NameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue && p1NameField.getText().isEmpty()){
                p1NameField.setText("Player 1");
            }
        });
        TextField p2NameField = new TextField();
        p2NameField.setText("Player 2");
        p2NameField.setFont(joystix);
        p2NameField.setMaxWidth(200);
        p2NameField.setOnMouseClicked(e -> {
            if (p2NameField.getText().equals("Player 2")) {
                p2NameField.setText("");
            }
        });
        p2NameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue && p2NameField.getText().isEmpty()){
                p2NameField.setText("Player 2");
            }
        });
        isTerrainEnabled = false;

        Button terrainToggleButton = new Button("Toggle Terrain");
        terrainToggleButton.setFont(joystix);
        terrainToggleButton.setOnAction(e -> {
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            if (isTerrainEnabled) {
                isTerrainEnabled = false;
                terrainToggleButton.setText("Terrain Disabled");
            } else {
                isTerrainEnabled = true;
                terrainToggleButton.setText("Terrain Enabled");
            }
        });


        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> {
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            controller.startLocalMultiplayerGame(p1NameField.getText(), p2NameField.getText(), isTerrainEnabled);
        });
        startButton.setFont(joystix);
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            controller.showGameMode();
        });
        backButton.setFont(joystix);

        layout.getChildren().addAll(label4, p1NameField, p2NameField, terrainToggleButton, startButton, backButton);
        layout.setAlignment(Pos.CENTER);

        primaryStage.setScene(scene);
    }

    public void showSingleplayer() { // Show singleplayer menu
        VBox layout = new VBox(10);
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

        p1NameField.setMaxWidth(200);
        p1NameField.setText("Player 1");
        p1NameField.setFont(joystix);
        p1NameField.setOnMouseClicked(e -> {
            if (p1NameField.getText().equals("Player 1")) {
                p1NameField.setText("");
            }
        });
        p1NameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue && p1NameField.getText().isEmpty()){
                p1NameField.setText("Player 1");
            }
        });


        MenuButton menuButton = new MenuButton("");
        menuButton.setMinWidth(200);
        menuButton.setAlignment(Pos.CENTER);
        MenuItem Easy = new MenuItem("Easy");
        Easy.setStyle("-fx-font-family: joystix; -fx-font-size: 24;");

        MenuItem Medium = new MenuItem("Medium");
        Medium.setStyle("-fx-font-family: joystix; -fx-font-size: 24;");

        MenuItem Hard = new MenuItem("Hard");
        Hard.setStyle("-fx-font-family: joystix; -fx-font-size: 24;");

        menuButton.getItems().addAll(Easy, Medium, Hard);
        menuButton.setText("Easy");
        menuButton.getItems().forEach(menuItem -> menuItem.setOnAction(event -> {
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            menuButton.setText(menuItem.getText());
        }));
        menuButton.setFont(joystix);

        isTerrainEnabled = false;

        Button terrainToggleButton = new Button("Toggle Terrain");
        terrainToggleButton.setFont(joystix);
        terrainToggleButton.setOnAction(e -> {
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            if (isTerrainEnabled) {
                isTerrainEnabled = false;
                terrainToggleButton.setText("Terrain Disabled");
            } else {
                isTerrainEnabled = true;
                terrainToggleButton.setText("Terrain Enabled");
            }
        });

        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> {
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            controller.startSingleplayerGame(p1NameField.getText(), menuButton.getText(), isTerrainEnabled);
        });
        startButton.setFont(joystix);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            controller.showGameMode();
        });
        backButton.setFont(joystix);

        layout.getChildren().addAll(label3, menuButton, label5, p1NameField, terrainToggleButton, startButton, backButton);
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

    public void showGameplay(String turn, Boolean multiplayer, Player currentplayer, Player opponent) { // Show gameplay scene
        HBox hbox;
        // Making the Stats Panel
        Button saveButton = new Button("Save & Quit"); // Save and quit button
        saveButton.setOnAction(e -> { // Save and quit button action, throws exception if it takes too long
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<?> future = executor.submit(() -> {
                try {
                    controller.saveGame();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
            executor.shutdown();
            try {
                future.get(5, TimeUnit.SECONDS); // wait for 5 seconds
                // the task completed successfully
                Platform.exit(); // exit the application
            } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                future.cancel(true); // interrupt the task if it takes longer than 5 seconds
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Can not save game");
                alert.setContentText("The operation took too long to complete.");
                alert.showAndWait();
            }
        });
        saveButton.setFont(joystixSave); // setting font of the saveButton


        Button noSaveButton = new Button("Quit without saving"); // Quit without saving button
        noSaveButton.setOnAction(e -> {
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            try {
                controller.showMainMenu();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        noSaveButton.setFont(joystixSave);

        // Showing the Current Player's name
        Label playerName = new Label(); // shows the name of the current player in an HBox
        String displayName;
        if (currentplayer.getName().equals("")) {
            displayName = "Player " + turn;
        } else {
            displayName = currentplayer.getName();
        }
        playerName.setText(displayName);
        playerName.setFont(joystix);
        HBox playerBox = new HBox();
        playerBox.getChildren().add(playerName);
        playerBox.setAlignment(Pos.CENTER);
        playerBox.setStyle("-fx-background-color: rgba(81, 162, 0, 0.8); -fx-border-color: black; -fx-border-radius: 10; -fx-background-insets: 5px");


        // Showing the Current Player's stats
        VBox playerStatsBox;
        if (turn.equals("1")) {
            playerStatsBox = getStatsPanel1();
        } else {
            playerStatsBox = getStatsPanel2();
        }
        // Putting the side panel together
        VBox panel = new VBox(10, playerBox,playerStatsBox, saveButton, noSaveButton);
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setPadding(new Insets(10));
        panel.setMaxWidth(400);
        panel.setMaxHeight(screenHeight);

        if (turn.equals("1") ) {
            container1.hideSidePanel();

            container1.setScaleX(0.6);
            container1.setScaleY(0.6);
            container1.setTranslateX(-150);

            shotContainer1.setScaleY(0.6);
            shotContainer1.setScaleX(0.6);

            container1.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
            shotContainer1.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");

            // labeling the player's board
            Label playerBoardLabel = new Label(displayName + "'s Board");
            HBox playerBoardBox = new HBox(playerBoardLabel);
            playerBoardBox.setAlignment(Pos.CENTER);
            playerBoardBox.setMaxWidth(455);
            playerBoardLabel.setFont(joystixSave);
            playerBoardBox.setTranslateX(65);
            playerBoardBox.setTranslateY(150);
            playerBoardBox.setStyle("-fx-background-color: rgba(81, 162, 0, 0.8); -fx-border-color: black; -fx-border-radius: 5; -fx-background-insets: 2px;");
            VBox placeBox = new VBox(playerBoardBox, container1);

            // Labeling results board
            Label resultsBoardLabel = new Label("Results from shots at:" + opponent.getName());
            HBox resultsBoardBox = new HBox(resultsBoardLabel);
            resultsBoardBox.setMaxWidth(455);
            resultsBoardLabel.setFont(joystixSave);
            resultsBoardBox.setAlignment(Pos.CENTER);
            resultsBoardBox.setTranslateX(100);
            resultsBoardBox.setTranslateY(150);
            resultsBoardBox.setStyle("-fx-background-color: rgba(81, 162, 0, 0.8); -fx-border-color: black; -fx-border-radius: 5; -fx-background-insets: 2px;");
            VBox shotBox = new VBox(resultsBoardBox, shotContainer1);

            hbox = new HBox(placeBox, shotBox);
            hbox.setMaxWidth(screenWidth - 400);
            hbox.setMaxHeight(screenHeight);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setSpacing(-500);

            hbox.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");



            HBox outerBox = new HBox(hbox, panel);
            outerBox.setMaxWidth(screenWidth);
            outerBox.setMaxHeight(screenHeight);
            outerBox.setSpacing(100);



            Image image = new Image("cucuer_back.jpg");
            BackgroundSize backgroundSize = new BackgroundSize(screenHeight, screenWidth, false, false, false, true);
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            outerBox.setBackground(new Background(backgroundImage));
            attackScene1 = new Scene(outerBox, screenWidth, screenHeight);
            primaryStage.setScene(attackScene1);
        } else if (turn.equals("2") && multiplayer) {
            container2.hideSidePanel();
            container2.setScaleX(0.6);
            container2.setScaleY(0.6);
            container2.setTranslateX(-150);

            shotContainer2.setScaleY(0.6);
            shotContainer2.setScaleX(0.6);

            container2.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
            shotContainer2.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");

            // labeling the player's board
            Label playerBoardLabel = new Label(displayName + "'s Board");
            HBox playerBoardBox = new HBox(playerBoardLabel);
            playerBoardBox.setAlignment(Pos.CENTER);
            playerBoardBox.setMaxWidth(455);
            playerBoardLabel.setFont(joystixSave);
            playerBoardBox.setTranslateX(65);
            playerBoardBox.setTranslateY(150);
            playerBoardBox.setStyle("-fx-background-color: rgba(81, 162, 0, 0.8); -fx-border-color: black; -fx-border-radius: 5; -fx-background-insets: 2px;");
            VBox placeBox = new VBox(playerBoardBox, container2);

            // Labeling results board
            Label resultsBoardLabel = new Label("Results from shots at:" + opponent.getName());
            HBox resultsBoardBox = new HBox(resultsBoardLabel);
            resultsBoardBox.setAlignment(Pos.CENTER);
            resultsBoardBox.setMaxWidth(455);
            resultsBoardLabel.setFont(joystixSave);
            resultsBoardBox.setTranslateX(100);
            resultsBoardBox.setTranslateY(150);
            resultsBoardBox.setStyle("-fx-background-color: rgba(81, 162, 0, 0.8); -fx-border-color: black; -fx-border-radius: 5; -fx-background-insets: 2px;");

            VBox shotBox = new VBox(resultsBoardBox, shotContainer2);

            hbox = new HBox(placeBox, shotBox);
            hbox.setMaxWidth(screenWidth - 400);
            hbox.setMaxHeight(screenHeight);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setSpacing(-500);

            hbox.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");

            HBox outerBox = new HBox(hbox, panel);
            outerBox.setMaxWidth(screenWidth);
            outerBox.setMaxHeight(screenHeight);
            outerBox.setSpacing(100);



            Image image = new Image("cucuer_back.jpg");
            BackgroundSize backgroundSize = new BackgroundSize(screenHeight, screenWidth, false, false, false, true);
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            outerBox.setBackground(new Background(backgroundImage));
            attackScene2 = new Scene(outerBox, screenWidth, screenHeight);
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
        gifPlayer.stop();
        VBox layout = new VBox();
        Scene scene = new Scene(layout, screenWidth, screenWidth);
        BackgroundImage image = new BackgroundImage(new Image("rick_rolled.gif"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, new BackgroundSize(layout.getWidth(), layout.getHeight(), false, false, false, false));
        layout.setBackground(new Background(image));

        finalSound.play();

        Label label1 = new Label("Winner is " + winner.getName());
        label1.setFont(joystixTitle);
        label1.setEffect(new DropShadow());

        Button backButton = new Button("Go to main menu");
        backButton.setOnAction(e -> {
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
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
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            menuButton.setText(menuItem.getText());
        }));
        menuButton.setFont(joystix);

        Button startButton = new Button("Load Game");
        startButton.setOnAction(e -> {
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            try {
                controller.loadGame(menuButton.getText());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        startButton.setFont(joystix);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            controller.showMainMenu();
        });
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
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
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
        backButton.setOnAction(e -> {
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            controller.showGameplay();
        });
        backButton.setFont(joystix);

        layout.getChildren().addAll(label3, gameNameField, startButton, backButton);
        layout.setAlignment(Pos.CENTER);

        primaryStage.setScene(scene);

    }
    private String getPickleGif(Gurkin gurk) {
        if (gurk instanceof Gherkin) {
            this.gifPlayer  = new MediaPlayer(new Media(new File("src/main/resources/BlenderPickle.mp3").toURI().toString()));
            return "BlenderPickle.gif";
        } else if (gurk instanceof Yardlong) {
            this.gifPlayer  = new MediaPlayer(new Media(new File("src/main/resources/YardlongDead.mp3").toURI().toString()));
                return "LateNightPickle.gif";
        } else if (gurk instanceof Conichon) {
            this.gifPlayer  = new MediaPlayer(new Media(new File("src/main/resources/BlenderPickle.mp3").toURI().toString()));
            return "PickleCrush.gif";
        } else if (gurk instanceof Zuchinni) {
            this.gifPlayer  = new MediaPlayer(new Media(new File("src/main/resources/PickleEat.mp3").toURI().toString()));
            return "PickleEat.gif";
        } else if (gurk instanceof Pickle) {
            this.gifPlayer  = new MediaPlayer(new Media(new File("src/main/resources/PickleBomb.mp3").toURI().toString()));
            return "PickleBomb.gif";
        } else {
            this.gifPlayer  = new MediaPlayer(new Media(new File("src/main/resources/Terrain.mp3").toURI().toString()));
            return "Terrain.gif";
        }
    }


    public void displaykillGIFView(Gurkin gurk, Player currentPlayer, Player opponentPlayer) {
        VBox GIFbox = new VBox();
        GIFbox.setAlignment(Pos.CENTER);
        GIFbox.setSpacing(10);
        GIFbox.setPadding(new Insets(20));

        BackgroundSize backgroundSize = new BackgroundSize(screenWidth, screenHeight, false, false, false, true);
        Image image = new Image(getPickleGif(gurk));
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        GIFbox.setBackground(new Background(backgroundImage));

        Label Text = new Label("Oh no, " + currentPlayer.getName() + " has consumed " + opponentPlayer.getName() + "'s " + gurk.getClass().toString().substring(19));
        if (gurk instanceof Terrain) {
            Text.setText("Congrats, " + currentPlayer.getName() + " you receive coordinates for hitting this terrain!");
        }
        Text.setFont(joystix);
        Text.setTextFill(Color.WHITE);



        Button Continue = new Button("Continue");
        Continue.setFont(joystix);
        Continue.setOnAction(event -> {
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            gifPlayer.stop();
            controller.triggerEndTurn();
            mainMenuMusic.play();
        } );

        GIFbox.getChildren().addAll(Text, Continue);
        primaryStage.setScene(new Scene(GIFbox, screenWidth, screenHeight));
        mainMenuMusic.pause();
        gifPlayer.play();

    }

    public void showConfirmPlace(String turn, String playerName) {
        VBox layout = new VBox(30); // Main layout
        layout.setAlignment(Pos.CENTER);

        Label turnLabel = new Label("Turn of player " + turn); // Label to show whose turn it is
        turnLabel.setFont(joystixTitle);
        turnLabel.setAlignment(Pos.CENTER);

        Label playerMessage = new Label(playerName + ", please place your gurkins"); // Label to show whose turn it is
        playerMessage.setFont(joystix);
        playerMessage.setAlignment(Pos.CENTER);

        Button confirmButton = new Button("Continue"); // Button to confirm placement
        confirmButton.setFont(joystix);
        confirmButton.setOnAction(e -> {
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            controller.showPlacement();
        });

        layout.getChildren().addAll(turnLabel, playerMessage, confirmButton); // Add all elements to layout
        Image image = new Image("pickle_fight.png");
        BackgroundSize backgroundSize = new BackgroundSize(screenHeight, screenWidth, false, false, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        layout.setBackground(new Background(backgroundImage));

        primaryStage.setScene(new Scene(layout, screenWidth, screenHeight));

    }

    public void confirmAttackView(String turn, String playerName) {
        VBox layout = new VBox(30); // Main layout
        layout.setAlignment(Pos.CENTER);

        Label turnLabel = new Label("Turn of player " + turn); // Label to show whose turn it is
        turnLabel.setFont(joystixTitle);
        turnLabel.setAlignment(Pos.CENTER);

        Label playerMessage = new Label(playerName + ", please prepare to attack"); // Label to show whose turn it is
        playerMessage.setFont(joystix);
        playerMessage.setAlignment(Pos.CENTER);

        Button confirmButton = new Button("Continue"); // Button to confirm placement
        confirmButton.setFont(joystix);
        confirmButton.setOnAction(e -> {
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            controller.showGameplay();
        });

        layout.getChildren().addAll(turnLabel, playerMessage, confirmButton); // Add all elements to layout
        Image image = new Image("pickle_fight.png");
        BackgroundSize backgroundSize = new BackgroundSize(screenHeight, screenWidth, false, false, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        layout.setBackground(new Background(backgroundImage));
        primaryStage.setScene(new Scene(layout, screenWidth, screenHeight));

    }


    public StatsPanel getStatsPanel1() {
        statsPanel1.updateStats();
        return statsPanel1;
    }
    public StatsPanel getStatsPanel2() {
        statsPanel2.updateStats();
        return statsPanel2;
    }

}


