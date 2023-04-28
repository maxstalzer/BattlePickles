package Gui;

import Base.Direction;
import Base.Gurkins.*;
import Observers.PlayerObserver;
import Controller.Controller;
import Controller.Controller.*;
import javafx.geometry.Pos;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import java.util.ArrayList;

import javafx.scene.control.MenuItem;


import static Base.Direction.direction.Horizontal;
import static Base.Direction.direction.Vertical;

public class SidePanel extends VBox implements PlayerObserver {
    private Direction.direction dir;
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
        javafx.scene.control.Label label1 = new javafx.scene.control.Label("Select Gurk Direction  ©");
        label1.setStyle(
                "-fx-font-family: Joystix ;-fx-font-size: 18;-fx-border-color: transparent, black;");
        setAlignment(Pos.CENTER);

        Button Quit = new Button("Quit");
        Quit.setStyle( "-fx-font-family: Joystix ; -fx-font-size: 18;");
        Quit.setOnAction(e -> controller.showMainMenu());
        getChildren().add(Quit);




        getChildren().add(label1);

        MenuButton menu = new MenuButton("");
        menu.setStyle( "-fx-font-family: Joystix ; -fx-font-size: 18;");
        MenuItem horiz = new MenuItem("Horizontal");
        horiz.setStyle( "-fx-font-family: Joystix ; -fx-font-size: 18;");
        MenuItem vert = new MenuItem("Vertical");
        vert.setStyle( "-fx-font-family: Joystix ; -fx-font-size: 18;");
        menu.setText("Choose Direction");
        //see if we would like to use this as it is
        menu.getItems().addAll( horiz, vert);
        menu.getItems().forEach(menuItem -> menuItem.setOnAction(event -> {
            menu.setText(menuItem.getText());
        }));

        horiz.setOnAction(e -> setDir(Horizontal));
        vert.setOnAction(e -> setDir(Vertical));
        getChildren().add(menu);
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
