package Gui;

import Base.Coordinates;
import javafx.scene.canvas.Canvas;
import javafx.stage.Screen;
import javafx.scene.image.Image;

import java.awt.*;

public class Miss extends Canvas {
    public Miss(Coordinates coords) {
        double gridsize = 956 /12;
        setWidth(gridsize);
        setHeight(gridsize);

        Image image = new Image("O.png");
        getGraphicsContext2D().drawImage(image,0,0,gridsize,gridsize);
    }
}
