package Gui;

import Base.Coordinates;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

import java.io.File;

public class GridTile extends TilePane { //This class is the tiles in the grid
    double gridsize = 956 /12; //the size of each grid
    boolean isHit=false; //not implemented
    boolean hasGurk =false; //not implemented might not have to be

    private MediaPlayer hoverSound; //mediaplayer object used to play the sound

    public boolean isHit() {//not implemented
        return isHit;
    }

    public void setHit(boolean hit) {//not implemented
        isHit = hit;
    }

    public void setHasGurk(boolean hasGurk) {//not implemented
        this.hasGurk = hasGurk;
    }



    public boolean isHasGurk() {//not implemented
        return hasGurk;
    }


    Coordinates coords; // coordinates field

    public GridTile(Coordinates coords) { //Constructor for the gridtile takes the coordinates, these are given by a for loop in the sea class.
        this.coords=coords;
        this.setPrefHeight(gridsize); //sets the size of the gridtiles
        this.setPrefWidth(gridsize);

        setOnMouseEntered(new EventHandler<Event>() { //sets the opacity/Transparency to half when you mouse hovers over a gridtile
            @Override
            public void handle(Event event) {

                setOpacity(0.5);
                hoverSound  = new MediaPlayer(new Media(new File("src/main/resources/Hover.mp3").toURI().toString()));//makes a mediaplayer object which is used to play the sound
                hoverSound.play(); //plays the sound

            }
        });
        setOnMouseExited(new EventHandler<Event>() {
            @Override
            public void handle(Event event) { //sets the opacity to 1 /Transparency to 0 when you mouse moves out the gridtile
                setOpacity(1);
            }
        });
    }
}
