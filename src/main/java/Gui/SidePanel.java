package Gui;

import Base.Coordinates;
import Base.Direction;
import Base.Gurkins.*;
import Controller.Controller;
import io.cucumber.java.ro.Si;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.List;

import static Base.Direction.direction.Vertical;

public class SidePanel extends VBox {

    Controller controller;
    public SidePanel(Controller controller) {
        this.controller=controller;
        javafx.scene.control.Label label1 = new javafx.scene.control.Label("Choose Gurk Type ©");
        setAlignment(Pos.CENTER);

        Button Quit = new Button("Quit");
        Quit.setOnAction(e -> {
            try {
                controller.showMainMenu();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }

        });
        getChildren().add(Quit);



        getChildren().add(label1);

        getChildren().add(new SidePanelGurks( new Pickle(),controller,this));
        getChildren().add(new SidePanelGurks( new Yardlong(),controller,this));
        getChildren().add(new SidePanelGurks( new Zuchinni(),controller,this));
        getChildren().add(new SidePanelGurks( new Gherkin(),controller,this));
        getChildren().add(new SidePanelGurks( new Conichon(),controller,this));


    }
    public void addGurkToSidePanel(Gurkin gurk) {
        getChildren().add(new SidePanelGurks(gurk,controller,this));
    }
    public void removeGurkFromSidePanel(Gurkin gurk) {
        getChildren().remove(gurk);
    }
    public void updateSidePanel(List<Gurkin> gurks) {
        getChildren().removeAll();
        for (Gurkin gurk:gurks) {
            addGurkToSidePanel(gurk);
        }
    }

}
