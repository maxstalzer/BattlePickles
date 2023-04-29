package Gui;

import Base.Coordinates;
import Base.Gurkins.Gurkin;
import Base.Gurkins.Terrain;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import Controller.Controller;

public class Kill extends Canvas{
    public Kill(Coordinates coords,Controller controller) {
        double gridsize = 956/12;
        setWidth(gridsize);
        setHeight(gridsize);
        //Canvas canvas = new Canvas(gridsize,gridsize);
        if (controller.getGurkin(coords) instanceof Terrain) {
            Image image = new Image("Terrain.png");
            getGraphicsContext2D().drawImage(image,0,0,gridsize,gridsize);
        } else {
            Image image = new Image("killme.png");
            getGraphicsContext2D().drawImage(image,0,0,gridsize,gridsize);
        }

    }
}
