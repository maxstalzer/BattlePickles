package Gui;

import Base.*;
import Observers.PlayerObserver;
import Controller.Controller;
import Controller.Controller.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;



import static Base.Direction.direction.Horizontal;
import static Base.Direction.direction.Vertical;

public class SidePanel extends VBox implements PlayerObserver {
    private MediaPlayer buttonClick;
    private Direction.direction dir = Horizontal;
    private gurkinID gurktypeField;
    private Font joystix = Font.loadFont(getClass().getResourceAsStream("/joystix monospace.otf"), 12);

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
        setAlignment(Pos.CENTER);
        setSpacing(10);
        setPadding(new Insets(20));

//        setStyle("-fx-background-color: #7b647a; -fx-border-color: #168616; -fx-border-width: 5px; -fx-border-radius: 10px;");
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
        Font joystix = Font.loadFont(getClass().getResourceAsStream("/joystix monospace.otf"), 16);
        Font joystix2 = Font.loadFont(getClass().getResourceAsStream("/joystix monospace.otf"), 20);
        Label title = new Label("Choose the position of your gurkins");
        title.setFont(joystix2);
        Label label2 = new Label("Click gurkin to choose, click tile to place");
        label2.setFont(joystix);
        Label label1 = new javafx.scene.control.Label("Press D to change direction ©");
        label1.setFont(joystix);
        setAlignment(Pos.CENTER);

        Button Quit = new Button("Quit");
        Quit.setFont(joystix);
        Quit.setOnAction(e -> controller.showMainMenu());
        getChildren().add(Quit);

        getChildren().addAll(title, label2, label1);

        String choice;
        if (dir == Vertical) {
            choice = "Current direction: Vertical";
        }
        else {
            choice = "Current direction: Horizontal";
        }

        Label DirChoice = new Label(choice);
        DirChoice.setFont(joystix);
        DirChoice.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-insets: 5px;");
        getChildren().add(DirChoice);

        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.D) {
                if (dir == Vertical) {
                    setDir(Horizontal);
                    DirChoice.setText("Current direction: Horizontal");
                }
                else {
                    setDir(Vertical);
                    DirChoice.setText("Current direction: Vertical");
                }
            }
        });


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

    public void showCheckPlacementPopup() {
        VBox popup = new VBox();
        popup.setAlignment(Pos.CENTER);
        popup.setSpacing(10);
        popup.setPadding(new Insets(20));
        popup.setStyle("-fx-background-color: rgba(81, 162, 0, 0.8); -fx-border-color: black; -fx-border-width: 5px; -fx-border-radius: 10px; -fx-background-insets: 5px;");
        Label label = new Label("Are you sure you want to place your gurkins here?");
        label.setFont(joystix);
        Button yes = new Button("Yes");
        yes.setOnAction(event -> {
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            controller.endPlacement();
        } );
        yes.setFont(joystix);
        Button no = new Button("Redo");
        no.setOnAction(event -> {
            buttonClick = new MediaPlayer(new Media(new File("src/main/resources/ButtonClick.mp3").toURI().toString()));
            buttonClick.play();
            controller.redoPlacement();
        });
        no.setFont(joystix);
        popup.getChildren().addAll(label,yes,no);
        getChildren().add(popup);
    }

}
