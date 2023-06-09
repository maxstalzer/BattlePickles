package Gui;

import Base.Coordinates;
import Base.Gurkin;
import Observers.PlayerAttackObserver;
import Observers.ResultObserver;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import Controller.Controller;

import java.io.File;

public class ShootingContainer extends Pane implements ResultObserver, PlayerAttackObserver {
    private Font joystix = Font.loadFont(getClass().getResourceAsStream("/joystix monospace.otf"), 18);
    private MediaPlayer hitSound;

    private Controller controller;

    double gridsize = 956 / 12;  // this defines the variable gridsize which is the size of a single grid on the sea. This is set to 1/12 of the monitor and is used widely in the other classes
    public ShootingContainer(Controller controller) {
        this.controller = controller;

        getChildren().add(new Sea()); //Here we add the sea as a child in the Pane ie. makes it contain an instance of the sea object


        setOnMouseClicked(new EventHandler<Event>() { //An event handler which activates when you click on the Container.
            @Override
            public void handle(Event event) {

                if (event.getTarget() instanceof GridTile) { // If what you clicked on was a Tile ("GridTile) execute this code
                    GridTile target = (GridTile) event.getTarget(); //save the GridTile Object as "target"
                    controller.makeShot(target.coords);
                }
            }

        });
    }

    @Override
    public void setHit(Coordinates coords) { // adds the hit image when the gurkin is hit
        Hit hit = new Hit(coords,controller);
        hit.relocate(coords.getX() * (gridsize), coords.getY() * gridsize);
        getChildren().add(hit);
        toFront();

        hitSound = new MediaPlayer(new Media(new File("src/main/resources/Hit.mp3").toURI().toString()));
        hitSound.play();
    }

    @Override
    public void setMiss(Coordinates coords) {
        Miss miss = new Miss(coords);
        miss.relocate(coords.getX() * (gridsize), coords.getY() * gridsize);
        getChildren().add(miss);
        toFront();
        hitSound = new MediaPlayer(new Media(new File("src/main/resources/Miss.mp3").toURI().toString()));
        hitSound.play();

    }
    @Override
    public void displayKillGIF(Gurkin gurk) {
        controller.displayKillGIFView(gurk);
    }

    @Override
    public void setKill(Coordinates coords) {
        Kill kill = new Kill(coords,controller);
        kill.relocate(coords.getX() * (gridsize), coords.getY() * gridsize);
        getChildren().add(kill);
        toFront();

    }


    @Override
    public void changeTurn() {
        controller.changeTurnView();
    }



}
