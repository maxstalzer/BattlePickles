package Gui;

import Base.*;
import Base.Gurkins.*;
import Controller.Controller;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HorizontalDirection;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;

import java.io.FileInputStream;

public class GuiGurks extends Canvas {
    double gridsize = Screen.getPrimary().getBounds().getMaxY()/12;

    public GuiGurks(Coordinates coords, Gurkin gurktype, Direction.direction dir) { //Contructor for the Gurks.


        setImage(gurktype); // Calls the method defined below
        if (dir.equals(Direction.direction.Horizontal)) { //if the gurk needs to be horizontal rotate it
            getTransforms().add(new Rotate(270,gridsize,0)); //rotates 270 degrees with the top right corner as pivot
            setTranslateX(-gridsize); //corrects for the translation "invoked" by the rotation
        }
        setOnMouseEntered(new EventHandler<Event>() { //sets the opacity/Transparency to half when you mouse hovers over a gridtile
            @Override
            public void handle(Event event) {
                setOpacity(0.5);
            }
        });
        setOnMouseExited(new EventHandler<Event>() { //sets the opacity to 1 /Transparency to 0 when you mouse moves out the gridtile
            @Override
            public void handle(Event event) {
                setOpacity(1);
            }
        });

    }

    public GuiGurks(Coordinates coords, Controller.gurkinID gurkinID, Direction.direction dir) { //Contructor for the Gurks.

        Gurkin gurktype;

        if (gurkinID == Controller.gurkinID.Pickle) {
            gurktype = new Pickle();
        } else if (gurkinID == Controller.gurkinID.Gherkin) {
            gurktype = new Gherkin();
        } else if (gurkinID == Controller.gurkinID.Conichon) {
            gurktype  = new Conichon();
        } else if (gurkinID == Controller.gurkinID.Yardlong) {
            gurktype = new Yardlong();
        } else {
            gurktype = new Zuchinni();
        }

        setImage(gurktype); // Calls the method defined below
        if (dir.equals(Direction.direction.Horizontal)) { //if the gurk needs to be horizontal rotate it
            getTransforms().add(new Rotate(270,gridsize,0)); //rotates 270 degrees with the top right corner as pivot
            setTranslateX(-gridsize); //corrects for the translation "invoked" by the rotation
        }
        setOnMouseEntered(new EventHandler<Event>() { //sets the opacity/Transparency to half when you mouse hovers over a gridtile
            @Override
            public void handle(Event event) {
                setOpacity(0.5);
            }
        });
        setOnMouseExited(new EventHandler<Event>() { //sets the opacity to 1 /Transparency to 0 when you mouse moves out the gridtile
            @Override
            public void handle(Event event) {
                setOpacity(1);
            }
        });

    }

    public void setImage(Gurkin gurktype) { //This sets the image so that it is the same as the specified gurkin
        int scale =1;
        if (gurktype instanceof Pickle) {
            //int size=Pickle.getSize();
            int size = 3; // I would like for the size to be defined off the type instead, as it is above. Right now the sizes are arbitrary
            setWidth(gridsize);
            setHeight(gridsize*size);
            Image image = new Image("Pickle.png"); // makes an Image object which is passed to the drawImage function which draws the graphic on the canvas/GuiGurks Object
            getGraphicsContext2D().drawImage(image,0,0,gridsize*scale,gridsize*size*scale);
        } else if (gurktype instanceof Yardlong) {
            // int size=Pickle.getSize();
            int height = 5;
            setWidth(gridsize);
            setHeight(gridsize*height);
            Image image = new Image("Armenian.png");
            getGraphicsContext2D().drawImage(image,0,0,gridsize*scale,gridsize*height*scale);
        } else if (gurktype instanceof Zuchinni) {
            // int size=Pickle.getSize();
            int height = 4;
            setWidth(gridsize);
            setHeight(gridsize*height);
            Image image = new Image("Zucchini.png");
            getGraphicsContext2D().drawImage(image,0,0,gridsize*scale,gridsize*height*scale);
        } else if (gurktype instanceof Gherkin) {
            // int size=Pickle.getSize();
            int height = 3;
            setWidth(gridsize);
            setHeight(gridsize*height);
            Image image = new Image("gherkin.png");
            getGraphicsContext2D().drawImage(image,0,0,gridsize*scale,gridsize*height*scale);
        } else if (gurktype instanceof Conichon) {
            int height = 2;
            setWidth(gridsize);
            setHeight(gridsize*height);
            Image image = new Image("Conichon.png");
            getGraphicsContext2D().drawImage(image,0,0,gridsize*scale,gridsize*height*scale);
        }
    }




}
