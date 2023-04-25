package Gui;

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
public class TempShooting extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene tempshootingscene = new Scene(new ShootingContainer());

        primaryStage.setTitle("BattlePickles");
        primaryStage.setScene(tempshootingscene);
        primaryStage.show();
    }
}
