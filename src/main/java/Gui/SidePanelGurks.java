package Gui;

import Base.Coordinates;
import Base.Direction;
import Base.Gurkins.*;
import Controller.Controller;
import io.cucumber.java.ast.Ya;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;

import static Base.Direction.direction.Vertical;

public class SidePanelGurks extends GuiGurks {


    public Gurkin getGurktypeField() {
        return gurktypeField;
    }

    public void setGurktypeField(Gurkin gurktypeField) {
        this.gurktypeField = gurktypeField;
    }

    Gurkin gurktypeField;
    public SidePanelGurks( Gurkin gurktype,Controller controller,SidePanel sidePanel) {
        super(new Coordinates(0,0), gurktype, Vertical);
        setWidth(getWidth()*0.2);
        setHeight(getHeight()*0.2);


        if (gurktype instanceof Pickle) {
            setGurktypeField(new Pickle());
            setImage(new Pickle());
            relocate(sidePanel.getHeight()/6,sidePanel.getWidth()/2);
            toFront();
        } else if (gurktype instanceof Yardlong) {
            setGurktypeField(new Yardlong());
            setImage(new Yardlong());
            relocate(2*sidePanel.getHeight()/6,sidePanel.getWidth()/2);
            toFront();
        } else if (gurktype instanceof Zuchinni) {
            setGurktypeField(new Zuchinni());
            setImage(new Zuchinni());
            relocate(3*sidePanel.getHeight()/6,sidePanel.getWidth()/2);
            toFront();
        } else if (gurktype instanceof Gherkin) {
            setGurktypeField(new Gherkin());
            setImage(new Gherkin());
            relocate(4*sidePanel.getHeight()/6,sidePanel.getWidth()/2);
            toFront();
        } else if (gurktype instanceof Conichon) {
            setGurktypeField(new Conichon());
            setImage(new Conichon());
            relocate(5*sidePanel.getHeight()/6,sidePanel.getWidth()/2);
            toFront();
        }
        setOnMouseClicked(new EventHandler<Event>() { //An event handler which activates when you click on the Container.
            @Override
            public void handle(Event event) {
                if (event.getTarget() instanceof Gurkin) {
                    Gurkin gurktype = (Gurkin) event.getTarget();

                    if (gurktype instanceof Pickle) {
                        setGurktypeField(new Pickle());
                    } else if (gurktype instanceof Yardlong) {
                        setGurktypeField(new Yardlong());
                    } else if (gurktype instanceof Zuchinni) {
                        setGurktypeField(new Zuchinni());
                    } else if (gurktype instanceof Gherkin) {
                        setGurktypeField(new Gherkin());
                    }
                }
            }

        });
    }


}

