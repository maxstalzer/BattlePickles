package Gui;

import Base.Coordinates;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.Screen;
public class Kill extends Canvas{
    public Kill(Coordinates coords) {
        double gridsize = Screen.getPrimary().getBounds().getMaxY()/12;
        setWidth(gridsize);
        setHeight(gridsize);
        //Canvas canvas = new Canvas(gridsize,gridsize);
        Image image = new Image("killme.png");
       // canvas.getGraphicsContext2D().drawImage(image,0,0,gridsize,gridsize);
        getGraphicsContext2D().drawImage(image,0,0,gridsize,gridsize);
    }
}
