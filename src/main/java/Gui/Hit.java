package Gui;

import Base.Coordinates;
import Base.Terrain;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import Controller.Controller;

public class Hit extends Canvas {

    public Hit(Coordinates coords,Controller controller) {

        double gridsize = 956 /12;
        setWidth(gridsize);
        setHeight(gridsize);
        if (controller.getGurkin(coords) instanceof Terrain) {
            Image image = new Image("Terrain.png");
            getGraphicsContext2D().drawImage(image,0,0,gridsize,gridsize);
            Image image2 = new Image("x.png");
            getGraphicsContext2D().drawImage(image2,0,0,gridsize,gridsize);
        } else {
            Image image = new Image("x.png");
            getGraphicsContext2D().drawImage(image,0,0,gridsize,gridsize);
        }
    }
}
