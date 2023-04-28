package Gui;

import Base.Direction;
import Base.Gurkins.*;
import Observers.PlayerObserver;
import Controller.Controller;
import Controller.Controller.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import java.util.ArrayList;


import static Base.Direction.direction.Horizontal;
import static Base.Direction.direction.Vertical;

public class SidePanel extends VBox implements PlayerObserver {
    private Direction.direction dir = Horizontal;
    private gurkinID gurktypeField;

    private Container container;

    public void setGurktypeField(gurkinID gurktypeField) {
        this.gurktypeField = gurktypeField;
    }
    public void clearGurktypeField() {
        this.gurktypeField = null;
    }

    public Direction.direction getDir() {
            return this.dir;
    }

    public void setDir(Direction.direction dir) {
            this.dir = dir;
    }

    public gurkinID getGurktypeField() {
        return gurktypeField;
    }

    Controller controller;
    public SidePanel(Controller controller, Container container) {
        this.controller=controller;
        this.container = container;
        initSidePanel();
    }

    public void initSidePanel() {
        getChildren().clear();
        initSidePanelTop();
        getChildren().add(new SidePanelGurks( new Pickle(),controller,this));
        getChildren().add(new SidePanelGurks( new Yardlong(),controller,this));
        getChildren().add(new SidePanelGurks( new Zuchinni(),controller,this));
        getChildren().add(new SidePanelGurks( new Gherkin(),controller,this));
        getChildren().add(new SidePanelGurks( new Conichon(),controller,this));

    }

    private void initSidePanelTop() {
        Label label1 = new javafx.scene.control.Label("Press D to change direction ©");
        label1.setStyle(
                "-fx-font-family: Joystix ;-fx-font-size: 18;-fx-border-color: transparent, black;");
        setAlignment(Pos.CENTER);

        Button Quit = new Button("Quit");
        Quit.setStyle( "-fx-font-family: Joystix ; -fx-font-size: 18;");
        Quit.setOnAction(e -> controller.showMainMenu());
        getChildren().add(Quit);

        getChildren().add(label1);

        Label DirChoice = new Label("Horizontal");
        DirChoice.setStyle(
                "-fx-font-family: Joystix ;-fx-font-size: 18;-fx-border-color: transparent, black;");

        getChildren().add(DirChoice);

        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.D) {
                if (dir == Vertical) {
                    setDir(Horizontal);
                    DirChoice.setText("Horizontal");
                }
                else {
                    setDir(Vertical);
                    DirChoice.setText("Vertical");
                }
            }
        });


        // getChildren().add(menu);
    }
    public void addGurkToSidePanel(Gurkin gurk) {
        getChildren().add(new SidePanelGurks(gurk,controller,this));
    }


    @Override
    public void updateSidePanel(ArrayList<Gurkin> gurks) {
        getChildren().clear();
        initSidePanelTop();
        for (Gurkin gurk:gurks) {
            if (!(container.getPlacedGurks().contains(gurk)))
            addGurkToSidePanel(gurk);
        }
    }

    @Override
    public void finalisePlacement() { // check if the player wants to place their gurkins again
        controller.checkPlacement();
    }
}
