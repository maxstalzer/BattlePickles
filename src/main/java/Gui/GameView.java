package Gui;

import Base.Coordinates;
import Base.Players.Player;
import Controller.Controller;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameView extends Application{

    private Controller controller; // Controller
    private Stage primaryStage; // Primary stage

    private Scene seaScene; // Sea scene

    public Container getContainer() {
        return container;
    }

    private Container container;



    public GameView() { // Constructor
    }

    @Override
    public void start(Stage primaryStage) { // Start method
        this.controller = new Controller(this);
        this.primaryStage = primaryStage;

        this.container = new Container(controller);
        this.seaScene = new Scene(container);
        controller.showMainMenu(); // Show main menu

    }

    public void setController(Controller controller) { // Setters
        this.controller = controller;

    }

    public Controller getController() {
        return controller;
    } // Getters


    public void startMainMenu() { // Start main menu
        VBox layout = new VBox();
        Scene scene = new Scene(layout, 500, 500);

        Label label1 = new Label("BattlePickles ©");
        label1.setFont(new Font("Arial Bold", 24));


        Button startButton = new Button("Start");
        startButton.setOnAction(e -> showGameMode());

        layout.getChildren().addAll(label1, startButton);
        layout.setAlignment(Pos.CENTER);
        primaryStage.setTitle("BattlePickles");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void showGameMode() { // Show game mode menu
        VBox layout = new VBox();
        Scene scene = new Scene(layout, 500, 500);

        Label label2 = new Label("Game mode");

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> controller.showMainMenu());

        Button AIButton = new Button("AI");
        AIButton.setOnAction(e -> controller.showSingleplayer());

        Button multiplayerButton = new Button("Multiplayer");
        multiplayerButton.setOnAction(e -> controller.showMultiplayer());

        layout.getChildren().addAll(label2, AIButton, multiplayerButton, backButton);
        layout.setAlignment(Pos.CENTER);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showMultiplayer() { // Show multiplayer menu
        VBox layout = new VBox();
        Scene scene = new Scene(layout, 500, 500);

        Label label4 = new Label("Input player names");

        TextField p1NameField = new TextField();
        p1NameField.setMaxWidth(100);
        TextField p2NameField = new TextField();
        p2NameField.setMaxWidth(100);

        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> controller.startMultiplayerGame(p1NameField.getText(), p2NameField.getText()));

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> controller.showGameMode());

        layout.getChildren().addAll(label4, p1NameField, p2NameField, startButton, backButton);
        layout.setAlignment(Pos.CENTER);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showSingleplayer() { // Show singleplayer menu
        VBox layout = new VBox();
        Scene scene = new Scene(layout, 500, 500);

        Label label3 = new Label("AI Difficulty");
        Label label5 = new Label("Player name");

        TextField p1NameField = new TextField();
        p1NameField.setMaxWidth(100);

        MenuButton menuButton = new MenuButton("");
        menuButton.getItems().addAll(new MenuItem("Easy"), new MenuItem("Medium"), new MenuItem("Hard"));

        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> controller.startSingleplayerGame(p1NameField.getText(), menuButton.getText()));

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> controller.showGameMode());

        layout.getChildren().addAll(label3, menuButton, label5, p1NameField, startButton, backButton);
        layout.setAlignment(Pos.CENTER);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showPlacement(Player player) { // Show placement scene
        primaryStage.setScene(seaScene);
    }



}
