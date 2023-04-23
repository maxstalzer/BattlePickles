package Gui;

import Base.Coordinates;
import Base.Gurkin;
import Base.Pickle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;

import java.io.FileInputStream;

public class GuiGurks extends Canvas {
    double gridwidth = Screen.getPrimary().getBounds().getMaxX()/12;
    double gridheight = Screen.getPrimary().getBounds().getMaxY()/12;
    public GuiGurks(Coordinates coords) {
        super(Screen.getPrimary().getBounds().getMaxX()/12,Screen.getPrimary().getBounds().getMaxY()/12);
        Image image = new Image("carrier.png");
        getGraphicsContext2D().drawImage(image,0,0,gridwidth,gridheight);

        setOnMouseEntered(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                setOpacity(0.5);
            }
        });
        setOnMouseExited(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                setOpacity(1);
            }
        });

    }




}
