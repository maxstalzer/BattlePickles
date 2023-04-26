package Gui;

import Base.Coordinates;
import Base.Direction;
import Base.Gurkins.*;
import Base.Players.ResultObserver;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HorizontalDirection;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;

import static Base.Direction.direction.Vertical;

public class ShootingContainer extends Pane implements ResultObserver {
    public ShootingContainer() {
        getChildren().add(new Sea()); //Here we add the sea as a child in the Pane ie. makes it contain an instance of the sea object
        double gridsize = Screen.getPrimary().getBounds().getMaxY() / 12; // this defines the variable gridsize which is the size of a single grid on the sea. This is set to 1/12 of the monitor and is used widely in the other classes

        setOnMouseClicked(new EventHandler<Event>() { //An event handler which activates when you click on the Container.
            @Override
            public void handle(Event event) {

                if (event.getTarget() instanceof GridTile) { // If what you clicked on was a Tile ("GridTile) execute this code
                    GridTile target = (GridTile) event.getTarget(); //save the GridTile Object as "target"
                    Shot shot = new Shot(target.coords);
                    shot.relocate(target.coords.getX() * (gridsize), target.coords.getY() * gridsize);
                    getChildren().add(shot);
                    toFront();

                }
            }

        });
    }

    @Override
    public void setHit(Coordinates coords) {

    }

    @Override
    public void setMiss(Coordinates coords) {

    }

    @Override
    public void setKill(Coordinates coords) {

    }
}
