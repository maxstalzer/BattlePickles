package Gui;

import Base.Coordinates;
import Base.Direction;
import Base.Gurkins.*;
import Base.Players.PlayerObserver;
import Controller.Controller;
import Controller.Controller.*;
import io.cucumber.java.ro.Si;
import javafx.geometry.Pos;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;


import static Base.Direction.direction.Horizontal;
import static Base.Direction.direction.Vertical;

public class SidePanel extends VBox implements PlayerObserver {
    private Direction.direction dir;
    private gurkinID gurktypeField;

    public void setGurktypeField(gurkinID gurktypeField) {
        this.gurktypeField = gurktypeField;
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
    public SidePanel(Controller controller) {
        this.controller=controller;
        javafx.scene.control.Label label1 = new javafx.scene.control.Label("Choose Gurk Type ©");
        setAlignment(Pos.CENTER);
        getChildren().add(label1);
        MenuButton menu = new MenuButton("Directions");
        MenuItem horiz = new MenuItem("Horizontal");
        MenuItem vert = new MenuItem("Vertical");
        horiz.setOnAction(e -> setDir(Horizontal));
        vert.setOnAction(e -> setDir(Vertical));

        menu.getItems().addAll(horiz,vert);
        getChildren().add(menu);



        getChildren().add(new SidePanelGurks( new Pickle(),controller,this));
        getChildren().add(new SidePanelGurks( new Yardlong(),controller,this));
        getChildren().add(new SidePanelGurks( new Zuchinni(),controller,this));
        getChildren().add(new SidePanelGurks( new Gherkin(),controller,this));
        getChildren().add(new SidePanelGurks( new Conichon(),controller,this));


    }
    public void addGurkToSidePanel(Gurkin gurk) {
        getChildren().add(new SidePanelGurks(gurk,controller,this));
    }

    public void gurkinRemoved(Gurkin gurk) {
        getChildren().removeAll();
    }

    @Override
    public void updateSidePanel(ArrayList<Gurkin> gurks) {
        getChildren().removeAll();
        for (Gurkin gurk:gurks) {
            addGurkToSidePanel(gurk);
        }
    }

    @Override
    public void finalisePlacement() {
//        Are you happy with your placement?
        // get input on happiness
        Boolean cont = true; // get the player input and use here.
        if (cont) {
            controller.endPlacement();
        } else {
            controller.redoPlacement();

        }

    }

}
