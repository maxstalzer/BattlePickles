package Gui;

import Base.Coordinates;
import javafx.application.Application;
import javafx.scene.Scene;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.image.Image;
//import javafx.scene.layout.*;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import Controller.Controller;

public class mainGui extends Application {
    Controller controller;


    public static void startGUI() {
        launch();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        Container container= new Container(); //instanceates the Container Object
        Scene seascene = new Scene(container); // Scene is a javafx object which is the thing that gets displayed on the stage



        VBox layout = new VBox();
        VBox layout2 = new VBox();
        VBox layout3 = new VBox();
        VBox layout4 = new VBox();

        layout.setAlignment(Pos.CENTER);
        layout2.setAlignment(Pos.CENTER);
        layout3.setAlignment(Pos.CENTER);
        layout4.setAlignment(Pos.CENTER);

        Scene Start = new Scene(layout, 500, 500);
        Scene GameMode = new Scene(layout2, 500, 500);
        Scene Multiplayer = new Scene(layout3, 500, 500);
        Scene AI = new Scene(layout4, 500, 500);

        Label label1 = new Label("BattlePickles ©");
        label1.setFont(new Font("Arial Bold", 24));

        Label label2 = new Label("Game mode");
        Label label3 = new Label("AI Difficulty");
        Label label4 = new Label("Input player names");
        Label label5 = new Label("Player name");

        Button start = new Button("Start");
            start.setOnAction(e -> primaryStage.setScene(GameMode));


        Button back = new Button("Back");
        back.setTranslateX(-190);
        back.setTranslateY(-190);
        back.setOnAction(e -> primaryStage.setScene(Start));

        TextField P1Name = new TextField();
        P1Name.setMaxWidth(100);
        TextField P2Name = new TextField();
        P2Name.setMaxWidth(100);

        Button AIButton = new Button("AI");
        AIButton.setOnAction(e -> primaryStage.setScene(AI));

        MenuButton menuButton = new MenuButton("");
        menuButton.getItems().addAll(new MenuItem("Easy"), new MenuItem("Medium"), new MenuItem("Hard"));

        Button StartAI = new Button("Start Game");
        StartAI.setOnAction(e -> {
            primaryStage.setScene(seascene);
            Base.Game AIGame = new Base.Game(true);
            AIGame.getPlayer1().setName(P1Name.getText());
        });

        Button backAI = new Button("Back");
        backAI.setTranslateX(-190);
        backAI.setTranslateY(-190);
        backAI.setOnAction(e -> primaryStage.setScene(GameMode));

        Button MultiplayerButton = new Button("Multiplayer");
        MultiplayerButton.setOnAction(e -> primaryStage.setScene(Multiplayer));

        Button StartMultiplayer = new Button("Start Game");
        StartMultiplayer.setOnAction(e -> {
            primaryStage.setScene(seascene);
            Base.Game MultiplayerGame = new Base.Game(true);
            MultiplayerGame.getPlayer1().setName(P1Name.getText());
            MultiplayerGame.getPlayer2().setName(P2Name.getText());
        });

        Button backMultiplayer = new Button("Back");
        backMultiplayer.setTranslateX(-190);
        backMultiplayer.setTranslateY(-190);
        backMultiplayer.setOnAction(e -> primaryStage.setScene(GameMode));

        layout.getChildren().addAll(label1, start);
        layout2.getChildren().addAll(label2, AIButton, MultiplayerButton, back);
        layout3.getChildren().addAll(label4, P1Name, P2Name, StartMultiplayer, backMultiplayer);
        layout4.getChildren().addAll(label3, menuButton, label5, P1Name, StartAI, backAI);

        primaryStage.setTitle("BattlePickles");
        primaryStage.setScene(Start);

        primaryStage.show();


    }


}




