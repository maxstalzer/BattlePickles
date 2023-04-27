package Gui;

import Base.BoardObserver;
import Base.Coordinates;
import Base.Direction;
import Base.Gurkins.*;
import Controller.Controller;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HorizontalDirection;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;


import javax.swing.border.Border;
import java.awt.*;
import java.util.List;

import static Base.Direction.direction.Horizontal;
import static Base.Direction.direction.Vertical;

public class Container extends Pane implements BoardObserver{ //This is the container that contains the Sea class
    Coordinates Gurks;
    double gridsize = Screen.getPrimary().getBounds().getMaxY()/12; // this defines the variable gridsize which is the size of a single grid on the sea. This is set to 1/12 of the monitor and is used widely in the other classes

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

    Controller controller;
    Sea sea;

    public Container(Controller controller) {
        sea = new Sea();
//        setCenter(sea);
        sidepanel = new SidePanel(controller);
////        setRight(sidepanel);
        this.controller = controller;
//        getChildren().add(new Sea());
//        getChildren().add(sidepanel);

        HBox hbox = new HBox();
        hbox.getChildren().add(sea);
        hbox.getChildren().add(sidepanel);

        getChildren().add(hbox);

        setOnMouseClicked(new EventHandler<Event>() { //An event handler which activates when you click on the Container.
            @Override
            public void handle(Event event) {

                if (event.getTarget() instanceof GridTile) { // If what you clicked on was a Tile ("GridTile) execute this code

                    GridTile target = (GridTile) event.getTarget(); //save the GridTile Object as "target"
                    /*
                    GuiGurks gurk = new GuiGurks(target.coords,new Pickle(),Vertical); // create an instance of the type GuiGurk at the target coordinates. Here we need to have a way of specifying the two other arguments; gurktype and direction, respectively
                    gurk.relocate(target.coords.getX()*(gridsize),target.coords.getY()*gridsize); //This places the gurk on the target coordinates
                    getChildren().add(gurk); //this adds the gurk as a child on this object, ie the Container
                    toFront(); //Moves it to the front, so that it displays over the grid color
                    setGurkplace(target.coords);

                     */

                    controller.placeGurkin(target.coords,controller.getChosenDir(), controller.getChosenGurk());
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



    @Override
    public void placeGurkin(Coordinates coords, Direction.direction direction, Gurkin gurkin) {
        Controller.gurkinID gurktype = controller.gurkTranslate(gurkin);
        GuiGurks gurk = new GuiGurks(coords,gurktype,direction); // create an instance of the type GuiGurk at the target coordinates. Here we need to have a way of specifying the two other arguments; gurktype and direction, respectively
        gurk.relocate(coords.getX()*(gridsize),coords.getY()*gridsize); //This places the gurk on the target coordinates
        getChildren().add(gurk); //this adds the gurk as a child on this object, ie the Container
        toFront(); //Moves it to the front, so that it displays over the grid color

    }

    @Override
    public void tileHit(Coordinates coords) {
        Canvas cross = new Canvas(gridsize,gridsize);
        Image image = new Image("cross.png");
        cross.getGraphicsContext2D().drawImage(image,0,0,gridsize,gridsize);
    }

    //todo: fix that all gurkins are the same for player2

    public void hideSidePanel() {
        sidepanel.setVisible(false);
    }

    public void removeMouseClick() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                sea.getGridTiles(j,i).setOnMouseClicked(null);
            }
        }
    }

}
