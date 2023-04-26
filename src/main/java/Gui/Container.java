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

    public Coordinates getGurks() {
        return Gurks;
    }

    public void setGurks(Coordinates gurks) {
        Gurks = gurks;
    }

    public SidePanel getSidepanel() {
        return sidepanel;
    }

    public void setSidepanel(SidePanel sidepanel) {
        this.sidepanel = sidepanel;
    }

    SidePanel sidepanel;

    public Container(Controller controller) {
        setCenter(new Sea());
        double gridsize = Screen.getPrimary().getBounds().getMaxY()/12; // this defines the variable gridsize which is the size of a single grid on the sea. This is set to 1/12 of the monitor and is used widely in the other classes
        sidepanel = new SidePanel(controller);
        setRight(sidepanel);
        setOnMouseClicked(new EventHandler<Event>() { //An event handler which activates when you click on the Container.
            @Override
            public void handle(Event event) {

                if (event.getTarget() instanceof GridTile) { // If what you clicked on was a Tile ("GridTile) execute this code
                    GridTile target = (GridTile) event.getTarget();
                    // Need a way of specifying Gurkin and changing the direction
                    controller.placeGurkin(target.coords, Vertical, Controller.gurkinID.Yardlong);
                }
            }

        });
    }

    public void updateContainer(Coordinates coords, Gurkin gurk, Direction.direction direction) {
        GuiGurks guiGurk;

        if (gurk instanceof Gherkin)  {
            guiGurk = new GuiGurks(coords, new Gherkin(), direction);
            guiGurk.relocate(coords.getX() * (guiGurk.gridsize), coords.getY() * (guiGurk.gridsize));
            getChildren().add(guiGurk);
            toFront();
            setGurkplace(coords);
    }

    }
    public Coordinates getPosition() {
        return this.Gurks;
    }
    public void setGurkplace(Coordinates coords) {
        this.Gurks = coords;
    }

}
