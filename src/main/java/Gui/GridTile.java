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
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class GridTile extends TilePane {
    double gridwidth = Screen.getPrimary().getBounds().getMaxX()/12;
    double gridheight = Screen.getPrimary().getBounds().getMaxY()/12;
    boolean isHit=false;
    boolean hasGurk =false;
    private boolean mouseOver;

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public void setHasGurk(boolean hasGurk) {
        this.hasGurk = hasGurk;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isHasGurk() {
        return hasGurk;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }
    Coordinates coords;

    public GridTile(Coordinates coords) {
        this.coords=coords;
        this.setPrefHeight(gridheight);
        this.setPrefWidth(gridwidth);
        draw();
        setOnMouseEntered(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                setMouseOver(true);
                setOpacity(0.5);
            }
        });
        setOnMouseExited(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                setMouseOver(false);
                setOpacity(1);
            }
        });
        /*
        setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                setOpacity(0);
                GuiGurks gurk = new GuiGurks(coords);
                gurk.getGraphicsContext2D().clearRect(0,0,20,20);
                gurk.getGraphicsContext2D().save();
                getChildren().add(gurk);
                toFront();
            }

        });

         */
    }
    public void draw() {
    }
    }
