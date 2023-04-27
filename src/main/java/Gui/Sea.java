package Gui;

import Base.Coordinates;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

public class Sea extends GridPane {
    private GridTile[][] gridTiles = new GridTile[10][10];
    // double gridsize = Screen.getPrimary().getBounds().getMaxY()/12; //Size of the grids in the gridPane
    public Sea() {
        int size = 10;
        double s = 100; // side of rectangle
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                GridTile g = new GridTile(new Coordinates(j, i));
                g.setStyle("-fx-background-color: rgba(81, 162, 0, 0.5);");
                // The gridTiles becomes blue.
                add(g, j, i); //The gridTiles are added to the GridPane object.
                gridTiles[j][i] = g;
            }
        }

        // Set background image
        Image image = new Image("Brine copy.gif");
        BackgroundImage bgImage = new BackgroundImage(image, null, null, null, null);
        setBackground(new Background(bgImage));

        setGridLinesVisible(true); //The lines of the grid are visible.
    }

    public GridTile getGridTiles(int x, int y) {
        return gridTiles[x][y];
    }

}
