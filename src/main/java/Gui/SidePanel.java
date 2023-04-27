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
import javafx.scene.control.Button;

import java.awt.*;
import java.util.ArrayList;
import java.io.FileNotFoundException;
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
        MenuItem horiz = new MenuItem("");
        horiz.setStyle( "-fx-font-family: Joystix ; -fx-font-size: 18;");
        MenuItem vert = new MenuItem("Vertical");
        vert.setStyle( "-fx-font-family: Joystix ; -fx-font-size: 18;");
        menu.setText("Horizontal");
        //see if we would like to use this as it is
        menu.getItems().addAll( horiz, vert);
        getChildren().add(menu);
        //menu.getItems().forEach(menuItem -> menuItem.setOnAction(event -> {
        //   menu.setText(menuItem.getText());
        //}));

        horiz.setOnAction(e -> setDir(Horizontal));
        vert.setOnAction(e -> setDir(Vertical));



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
        // if yes, then
        controller.checkPlacement();
    }
}
