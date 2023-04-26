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

import static Base.Direction.direction.Vertical;

public class SidePanelGurks extends GuiGurks {


    public gurkinID getGurktypeField() {
        return gurktypeField;
    }

    public void setGurktypeField(gurkinID gurktypeField) {
        this.gurktypeField = gurktypeField;
    }

    gurkinID gurktypeField;
    public SidePanelGurks( Gurkin gurktype,Controller controller,SidePanel sidePanel) {
        super(new Coordinates(0,0), gurktype, Vertical);
        setWidth((getWidth()*0.5));
        setHeight((getHeight()*0.5));

        setScale(0.5);
        if (gurktype instanceof Pickle) {
            setImage(new Pickle());
            sidePanel.setGurktypeField(gurkinID.Pickle);
            toFront();
        } else if (gurktype instanceof Yardlong) {
            setImage(new Yardlong());
            sidePanel.setGurktypeField(gurkinID.Yardlong);
            toFront();
        } else if (gurktype instanceof Zuchinni) {
            sidePanel.setGurktypeField(gurkinID.Zuchinni);
            setImage(new Zuchinni());
            toFront();
        } else if (gurktype instanceof Gherkin) {
            sidePanel.setGurktypeField(gurkinID.Gherkin);
            setImage(new Gherkin());
            toFront();
        } else if (gurktype instanceof Conichon) {
            sidePanel.setGurktypeField(gurkinID.Conichon);
            setImage(new Conichon());
            toFront();
        }
        setOnMouseClicked(new EventHandler<Event>() { //An event handler which activates when you click on the Container.
            @Override
            public void handle(Event event) {
                System.out.println("Seen");
                SidePanelGurks gurk;
//                System.out.println(event.getTarget());
                if (event.getTarget() instanceof SidePanelGurks) {
                    System.out.println("gurkseen");
                    gurk = (SidePanelGurks) event.getTarget();

//                    System.out.println(gurk.gurktype);

                    if (gurk.gurktype instanceof Pickle) {
                        sidePanel.setGurktypeField(Controller.gurkinID.Pickle);
                        System.out.println("Pickle");
                    } else if (gurk.gurktype instanceof Yardlong) {
                        sidePanel.setGurktypeField(Controller.gurkinID.Yardlong);
                        System.out.println("2");
                    } else if (gurk.gurktype instanceof Zuchinni) {
                        sidePanel.setGurktypeField(Controller.gurkinID.Zuchinni);
                        System.out.println("3");
                    } else if (gurk.gurktype instanceof Gherkin) {
                        sidePanel.setGurktypeField(Controller.gurkinID.Gherkin);
                        System.out.println("4");
                    } else if (gurk.gurktype instanceof Conichon) {
                        sidePanel.setGurktypeField(Controller.gurkinID.Conichon);
                        System.out.println("5");
                    }
                }
            }

        });
    }


}

