package Gui;

import Observers.BoardObserver;
import Base.Coordinates;
import Base.Direction;
import Base.Gurkins.*;
import Controller.Controller;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Container extends Pane implements BoardObserver{ //This is the container that contains the Sea class
    Coordinates Gurks;
    private MediaPlayer placingSound;

    private List<GuiGurks> placedGurks;
    double gridsize = Screen.getPrimary().getBounds().getMaxY()/12; // this defines the variable gridsize which is the size of a single grid on the sea. This is set to 1/12 of the monitor and is used widely in the other classes

    public Coordinates getGurks() {
        return Gurks;
    }

    private void removeGurkin (GuiGurks gurk) {
        getChildren().remove(gurk);
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
        Image image = new Image("brine copy.gif");
        BackgroundSize backgroundSize = new BackgroundSize(Screen.getPrimary().getBounds().getHeight(), Screen.getPrimary().getBounds().getWidth(), false, false, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        setBackground(new Background(backgroundImage));
        this.placedGurks = new ArrayList<>(); //This is a list of all the gurks that have been placed on the board
        sea = new Sea();
        sidepanel = new SidePanel(controller, this);
        this.controller = controller;

        HBox hbox = new HBox();
        hbox.getChildren().add(sea);
        hbox.getChildren().add(sidepanel);

        getChildren().add(hbox);

        setOnMouseClicked(new EventHandler<Event>() { //An event handler which activates when you click on the Container.
            @Override
            public void handle(Event event) {

                if (event.getTarget() instanceof GridTile) { // If what you clicked on was a Tile ("GridTile) execute this code

                    GridTile target = (GridTile) event.getTarget(); //save the GridTile Object as "target"'

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
        placedGurks.add(gurk);
        sidepanel.clearGurktypeField();
        this.placingSound  = new MediaPlayer(new Media(new File("src/main/resources/Squish Sound Effect.mp3").toURI().toString()));
        placingSound.play();
    }

    @Override
    public void tileHit(Coordinates coords) {
        Hit hit = new Hit(coords);
        hit.relocate(coords.getX()*(gridsize),coords.getY()*gridsize);
        getChildren().add(hit);
        toFront();
        // todo: add some kind of hit response?
    }

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

    public void resetPlacement() {
        for (GuiGurks gurk: placedGurks) {
            removeGurkin(gurk);
        }
        placedGurks.clear();
        sidepanel.initSidePanel();
    }

    public List<GuiGurks> getPlacedGurks() {
        return placedGurks;
    }

    public void showCheckPlacementPopup() {
        sidepanel.showCheckPlacementPopup();
    }

}
