package Gui;

import Base.Coordinates;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

public class Sea extends GridPane {
    double gridsize = Screen.getPrimary().getBounds().getMaxY()/12; //Size of the grids in the gridPane
    public Sea() { //constructor which loops over the size on the x and y axis and instanciates a GridTile object for each i and j, with these as their coordinates.
        int size = 10;
        double s = 100; // side of rectangle
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                GridTile g = new GridTile(new Coordinates(j, i));
                g.setStyle("-fx-background-color:blue;"); // The gridTiles becomes blue.
                add(g, j, i); //The gridTiles are added to the GridPane object.
            }
        }


        setStyle("-fx-background-color:black;"); //The GridPane Object / Background color is set to black
        setGridLinesVisible(true); //The lines of the grid are visible.
    }


}
