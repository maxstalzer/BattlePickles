package Gui;

import Observers.BoardStatsObserver;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import Base.Coordinates;
import Controller.Controller;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class StatsPanel extends VBox implements BoardStatsObserver {
    private int hitTiles;
    private int missTiles;
    private int totalShots;
    private int hitPercentage;
    private int missPercentage;
    private Controller controller;
    private Font joystix = Font.loadFont(getClass().getResourceAsStream("/joystix monospace.otf"), 18);
    private Font joystixTitle = Font.loadFont(getClass().getResourceAsStream("/joystix monospace.otf"), 50);
    private Font joystixSmall = Font.loadFont(getClass().getResourceAsStream("/joystix monospace.otf"), 12);
    private int gurkinsLeft;
    private int gurkinsKilled;
    private int player;

    private ArrayList<Coordinates> knownLocations;
    VBox knownLocationsBox;

    Label playerGurks;
    Label playerKills;
    Label playerHit;
    Label playerMiss;
    Label playerTotal;
    Label playerHitPercentage;
    Label playerMissPercentage;







    public StatsPanel(Controller controller, int player) {
        hitTiles = 0;
        missTiles = 0;
        totalShots = 0;
        hitPercentage = 0;
        this.controller = controller; // added controller to constructor
        this.player = player;
        this.knownLocations = new ArrayList<>();

        setSpacing(15); // sets the spacing between the elements in the vbox
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: rgba(81, 162, 0, 0.8); -fx-border-color: black; -fx-border-radius: 10;");
        setMaxWidth(350);
        setMinWidth(300);
        setMinHeight(700);

        Label playerStats = new Label("Player Stats");
        playerStats.setFont(joystix);
        playerGurks = new Label("Gurkins left:  " +  5);
        playerGurks.setFont(joystixSmall);

        playerKills = new Label("Gurkins killed:  " +  0);
        playerKills.setFont(joystixSmall);

        playerHit = new Label("Hit tiles:  " + hitTiles);
        playerHit.setFont(joystixSmall);
        playerMiss = new Label("Miss tiles:  " + missTiles);
        playerMiss.setFont(joystixSmall);
        playerTotal = new Label("Total shots:  " + totalShots);
        playerTotal.setFont(joystixSmall);
        playerHitPercentage = new Label("Hit percentage:  " + hitPercentage + "%");
        playerHitPercentage.setFont(joystixSmall);
        playerMissPercentage = new Label("Miss percentage:  " + missPercentage + "%");
        playerMissPercentage.setFont(joystixSmall);

        getChildren().addAll(playerStats, playerGurks, playerKills, playerHit, playerMiss, playerTotal, playerHitPercentage, playerMissPercentage);

        knownLocationsBox = new VBox(10);
        knownLocationsBox.setAlignment(Pos.CENTER);
        knownLocationsBox.setStyle("-fx-background-color: rgb(255,255,255); -fx-border-color: black; -fx-border-radius: 10;");
        updateknownLocations();


    }

    private void updateShipCounts() {
        gurkinsLeft = controller.getGurkinCount(player)[0];
        gurkinsKilled = controller.getGurkinCount(player)[1];
        playerGurks.setText("Gurkins left:  " +  gurkinsLeft);
        playerKills.setText("Gurkins killed:  " +  gurkinsKilled);

    }

    private void updatePercentage() {
        hitPercentage = (int) ((hitTiles / (double) totalShots) * 100);
        missPercentage = (int) ((missTiles / (double) totalShots) * 100);
        playerHitPercentage.setText("Hit percentage:  " + hitPercentage + "%");
        playerMissPercentage.setText("Miss percentage:  " + missPercentage + "%");
    }

    private void updateTiles() {

        playerHit.setText("Hit tiles:  " + hitTiles);
        playerMiss.setText("Miss tiles:  " + missTiles);
        playerTotal.setText("Total shots:  " + totalShots);
    }

    public void updateStats() {
        updateShipCounts();
        updateTiles();
        updatePercentage();
        getChildren().clear();
        Label playerStats = new Label("Player Stats");
        playerStats.setFont(joystix);
        getChildren().add(playerStats);
        getChildren().addAll(playerGurks, playerKills, playerHit, playerMiss, playerTotal, playerHitPercentage, playerMissPercentage);
        updateknownLocations();
    }



    @Override
    public void sendCoords(Coordinates coords) {
        knownLocations.add(coords);
    }

    @Override
    public void increaseHitTiles(int hits) {
        this.hitTiles += hits;
        updateStats();
    }

    @Override
    public void increaseMissTiles(int misses) {
        this.missTiles += misses;
        updateStats();
    }

    @Override
    public void increaseTotalShots(int shots) {
        this.totalShots += shots;
        updateStats();
    }

    private void updateknownLocations() {
        if (!knownLocations.isEmpty()) {
            knownLocationsBox.getChildren().clear();
            Label knownLocationsLabel = new Label("Known locations");
            knownLocationsLabel.setFont(joystix);
            knownLocationsBox.getChildren().add(knownLocationsLabel);
            for (Coordinates coords : knownLocations) {
                Label coordsLabel = new Label((coords.getX()+1)+ ", " + (coords.getY()+1));
                coordsLabel.setFont(joystixSmall);
                knownLocationsBox.getChildren().add(coordsLabel);
            }
            getChildren().add(knownLocationsBox);
        }
    }
    @Override
    public void removeCoords(Coordinates coords) {
        for (Coordinates c : knownLocations) {
            if (c.getX() == coords.getX() && c.getY() == coords.getY()) {
                knownLocations.remove(c);
                break;
            }
        }
    }
}
