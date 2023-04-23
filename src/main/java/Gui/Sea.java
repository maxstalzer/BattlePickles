package Gui;

import Base.Coordinates;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

public class Sea extends GridPane {
    double gridwidth = Screen.getPrimary().getBounds().getMaxX()/12;
    double gridheight = Screen.getPrimary().getBounds().getMaxY()/12;
    public Sea() {
        int size = 10;
        double s = 100; // side of rectangle
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                GridTile g = new GridTile(new Coordinates(j, i));
                g.setStyle("-fx-background-color:blue;"); // skal fikses
                g.setOpacity(1);
                add(g, j, i);
            }
        }
        setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (event.getTarget() instanceof GridTile) {

                }
            };
        });

        setStyle("-fx-background-color:black;");
        setGridLinesVisible(true);
    }


}
