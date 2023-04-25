package Gui;

import Base.Coordinates;
import Base.Direction;
import Base.Gurkins.*;
import Controller.Controller;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HorizontalDirection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;

import java.util.List;

import static Base.Direction.direction.Horizontal;
import static Base.Direction.direction.Vertical;

public class Container extends BorderPane { //This is the container that contains the Sea class
    Coordinates Gurks;
    public Container(Controller controller) {

        getChildren().add(new Sea()); //Here we add the sea as a child in the Pane ie. makes it contain an instance of the sea object
        double gridsize = Screen.getPrimary().getBounds().getMaxY()/12; // this defines the variable gridsize which is the size of a single grid on the sea. This is set to 1/12 of the monitor and is used widely in the other classes

        setRight(new SidePanel());
        setOnMouseClicked(new EventHandler<Event>() { //An event handler which activates when you click on the Container.
            @Override
            public void handle(Event event) {

                if (event.getTarget() instanceof GridTile) { // If what you clicked on was a Tile ("GridTile) execute this code
                    GridTile target = (GridTile) event.getTarget(); //save the GridTile Object as "target"
                    GuiGurks gurk = new GuiGurks(target.coords, new Yardlong(),Vertical); // create an instance of the type GuiGurk at the target coordinates. Here we need to have a way of specifying the two other arguments; gurktype and direction, respectively
                    gurk.relocate(target.coords.getX()*(gridsize),target.coords.getY()*gridsize); //This places the gurk on the target coordinates
                    getChildren().add(gurk); //this adds the gurk as a child on this object, ie the Container
                    toFront(); //Moves it to the front, so that it displays over the grid color

                    setGurkplace(target.coords);
                    controller.placeGurksSingleplayer();

                }
            }

        });
    }
    public Coordinates getPosition() {
        return this.Gurks;
    }
    public void setGurkplace(Coordinates coords) {
        this.Gurks = coords;
    }

}
