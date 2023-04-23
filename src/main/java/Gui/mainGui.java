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
        Container container= new Container();
        Scene seascene = new Scene(container);

        primaryStage.setTitle("Battle-board");
        primaryStage.setScene(seascene);
        /*
        Canvas temp1 = new GuiGurks(new Coordinates(1,2));
        Canvas temp3 = new Canvas(200,200);
        temp3.getGraphicsContext2D().fillRect(0,0,20,20);
        temp3.getGraphicsContext2D().setFill(Color.BLACK);
        StackPane temp2 = new StackPane(temp3);
        Scene temp = new Scene(temp2);
        primaryStage.setScene(temp);
         */
        primaryStage.show();
    }
    public static void main(String[] args){ launch(args);
    } }




