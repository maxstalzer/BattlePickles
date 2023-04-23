package Gui;

import Base.Coordinates;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class mainGui extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Container container= new Container(); //instanceates the Container Object
        Scene seascene = new Scene(container); // Scene is a javafx object which is the thing that gets displayed on the stage

        primaryStage.setTitle("Battle-board"); //primary stage is the stage that gets displayed on the monitor.
        primaryStage.setScene(seascene);
        primaryStage.show();
    }
    public static void main(String[] args){ launch(args);
    } }




