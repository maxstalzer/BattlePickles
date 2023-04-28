package Gui;

import Base.Coordinates;
import Base.Direction;
import Base.Gurkins.*;
import Controller.Controller;
import Controller.Controller.*;
import io.cucumber.java.ast.Ya;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

import static Base.Direction.direction.Horizontal;
import static Base.Direction.direction.Vertical;

public class SidePanelGurks extends GuiGurks {

    private MediaPlayer gurkSound;
    public gurkinID getGurktypeField() {
        return gurktypeField;
    }

    public void setGurktypeField(gurkinID gurktypeField) {
        this.gurktypeField = gurktypeField;
    }

    gurkinID gurktypeField;
    public SidePanelGurks( Gurkin gurktype,Controller controller,SidePanel sidePanel) {
        super(new Coordinates(0,0), gurktype, Horizontal);
        setWidth((getWidth()*0.5));
        setHeight((getHeight()*0.5));

        setScale(0.5);
        if (gurktype instanceof Pickle) {
            setImage(new Pickle());
            setGurktypeField(gurkinID.Pickle);
            toFront();
        } else if (gurktype instanceof Yardlong) {
            setImage(new Yardlong());
            setGurktypeField(gurkinID.Yardlong);
            toFront();
        } else if (gurktype instanceof Zuchinni) {
            setGurktypeField(gurkinID.Zuchinni);
            setImage(new Zuchinni());
            toFront();
        } else if (gurktype instanceof Gherkin) {
            setGurktypeField(gurkinID.Gherkin);
            setImage(new Gherkin());
            toFront();
        } else if (gurktype instanceof Conichon) {
            setGurktypeField(gurkinID.Conichon);
            setImage(new Conichon());
            toFront();
        }
        setOnMouseClicked(new EventHandler<Event>() { //An event handler which activates when you click on the Container.
            @Override
            public void handle(Event event) {
                SidePanelGurks gurk;

                if (event.getTarget() instanceof SidePanelGurks) {

                    gurk = (SidePanelGurks) event.getTarget();

                    if (gurk.getGurktypeField().equals(gurkinID.Pickle)) {
                        sidePanel.setGurktypeField(Controller.gurkinID.Pickle);
                        gurkSound = new MediaPlayer(new Media(new File("src/main/resources/PickleSelected.mp3").toURI().toString()));
                        gurkSound.setVolume(1);
                        gurkSound.play();
                        System.out.println("Pickle");
                    } else if (gurk.getGurktypeField().equals(gurkinID.Yardlong)) {
                        sidePanel.setGurktypeField(Controller.gurkinID.Yardlong);
                        gurkSound = new MediaPlayer(new Media(new File("src/main/resources/YardlongSelected.mp3").toURI().toString()));
                        gurkSound.play();
                        System.out.println("2");
                    } else if (gurk.getGurktypeField().equals(gurkinID.Zuchinni)) {
                        sidePanel.setGurktypeField(Controller.gurkinID.Zuchinni);
                        gurkSound = new MediaPlayer(new Media(new File("src/main/resources/ZuchiniSelected.mp3").toURI().toString()));
                        gurkSound.play();
                        System.out.println("3");
                    } else if (gurk.getGurktypeField().equals(gurkinID.Gherkin)) {
                        sidePanel.setGurktypeField(Controller.gurkinID.Gherkin);
                        gurkSound = new MediaPlayer(new Media(new File("src/main/resources/GherkinSelected.mp3").toURI().toString()));
                        gurkSound.play();
                        System.out.println("4");
                    } else if (gurk.getGurktypeField().equals(gurkinID.Conichon)) {
                        sidePanel.setGurktypeField(Controller.gurkinID.Conichon);
                        gurkSound = new MediaPlayer(new Media(new File("src/main/resources/ConichonSelected.mp3").toURI().toString()));
                        gurkSound.play();
                        System.out.println("5");
                    }
                }
            }

        });
    }


}

