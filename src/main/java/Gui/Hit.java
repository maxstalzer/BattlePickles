package Gui;

import Base.Coordinates;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.Screen;

public class Hit extends Canvas {

    public Hit(Coordinates coords) {

        double gridsize = Screen.getPrimary().getBounds().getMaxY()/12;
        setWidth(gridsize);
        setHeight(gridsize);
        Image image = new Image("cross.png"); // makes an Image object which is passed to the drawImage function which draws the graphic on the canvas/Shot Object
        getGraphicsContext2D().drawImage(image,0,0,gridsize,gridsize);
    }
}
