package Gui;

import Base.Coordinates;
import Base.Players.ResultObserver;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import Controller.Controller;

public class ShootingContainer extends Pane implements ResultObserver {
    double gridsize = Screen.getPrimary().getBounds().getMaxY() / 12;  // this defines the variable gridsize which is the size of a single grid on the sea. This is set to 1/12 of the monitor and is used widely in the other classes
    public ShootingContainer(Controller controller) {
        getChildren().add(new Sea()); //Here we add the sea as a child in the Pane ie. makes it contain an instance of the sea object
        setOnMouseClicked(new EventHandler<Event>() { //An event handler which activates when you click on the Container.
            @Override
            public void handle(Event event) {

                if (event.getTarget() instanceof GridTile) { // If what you clicked on was a Tile ("GridTile) execute this code
                    GridTile target = (GridTile) event.getTarget(); //save the GridTile Object as "target"
//                    Shot shot = new Shot(target.coords);
//                    shot.relocate(target.coords.getX() * (gridsize), target.coords.getY() * gridsize);
//                    getChildren().add(shot);
//                    toFront();
                    controller.makeShot(target.coords);
                }
            }

        });
    }

    @Override
    public void setHit(Coordinates coords) {
        System.out.println("Hit at: " + coords.getX() + ", " + coords.getY());
        Hit hit = new Hit(coords);
        hit.relocate(coords.getX() * (gridsize), coords.getY() * gridsize);
        getChildren().add(hit);
        toFront();
    }

    @Override
    public void setMiss(Coordinates coords) {

    }

    @Override
    public void setKill(Coordinates coords) {

    }
}
