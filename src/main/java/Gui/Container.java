package Gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

public class Container extends Pane {
    public Container() {
        getChildren().add(new Sea());
        double gridwidth = Screen.getPrimary().getBounds().getMaxX()/12;
        double gridheight = Screen.getPrimary().getBounds().getMaxY()/12;


        setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {

                if (event.getTarget() instanceof GridTile) {
                    GridTile target = (GridTile) event.getTarget();
                    GuiGurks gurk = new GuiGurks(target.coords);
                    gurk.relocate(target.coords.getX()*(gridwidth),target.coords.getY()*gridheight);
                    getChildren().add(gurk);
                    toFront();
                }
            }

        });
    }
}
