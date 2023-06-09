package Base;


import Observers.PlayerAttackObserver;
import Observers.PlayerObserver;
import com.j256.ormlite.field.DatabaseField;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Player")
public class  Player implements PlayerObserver, PlayerAttackObserver{
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name; // The name of the player
    @DatabaseField
    private String difficultyString;
    @DatabaseField
    private int remaining_gurkins; //   The number of gurkins remaining to be shot
    @DatabaseField
    private Boolean CurrentPlayer;
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private Board gurkinBoard; // The board that the player uses to place their gurkins
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private ShotResults shotResults;// Stores results of shots
    private Set<PlayerObserver> playerObservers = new HashSet<PlayerObserver>();

    private Set<PlayerAttackObserver> attackObservers = new HashSet<PlayerAttackObserver>();

    public Player(int id, String name, Boolean CurrentPlayer) {
        this.id = id;
        this.name = name;
        this.CurrentPlayer = CurrentPlayer;
    }



    private ArrayList<Gurkin> unplacedGurks;
    public Board getGurkinBoard() {
        return gurkinBoard;
    } // the board of gurkins
    public void setGurkinBoard(Board b) {this.gurkinBoard = b;}
    public Player() { // Constructor

//      Setup the gurkin board
        this.gurkinBoard = new Board();
        this.remaining_gurkins = 0;
        //turnID = Turn.getTurn();
        this.shotResults = new ShotResults();
        this.unplacedGurks = new ArrayList<Gurkin>();
        unplacedGurks.add(new Pickle());
        unplacedGurks.add(new Gherkin());
        unplacedGurks.add(new Conichon());
        unplacedGurks.add(new Yardlong());
        unplacedGurks.add(new Zuchinni());

    }

    public void setCurrentPlayer(Boolean CurrentPlayer) {
        this.CurrentPlayer = CurrentPlayer;
    }


    public Character[][] getShotResults() {
        return shotResults.getShotBoard();
    } // Returns shot results
    public Collection<Result> getShotResultsCollection() {return shotResults.getShotCollection();}
    public ShotResults getShotRes() {return this.shotResults;}
    public void setShotRes(ShotResults shotResults) {this.shotResults = shotResults;}
    public void setName(String name) {
        this.name = name;
    } // Sets the name

    public String getName() {
        return name;
    } // returns players name

    public int getRemaining_gurkins() {return remaining_gurkins; } // gets the number of gurkins a player needs to kill

    public void setRemaining_gurkins(int val) {remaining_gurkins = val;} // sets the number of gurkins a player needs to kill


//  Allows a player to shoot at given coordinates on the opposing player's board

    public void shoot(Board board, Coordinates coords) {
        String result = board.attack(coords); // attack the tile at the coordinates
        if (result.equals("hit")) { // if the shot was a hit
            this.shotResults.setHit(coords);// set the shot results to a hit
            Gurkin gurk = board.getTile(coords).getGurkin(); // get the gurkin that was hit
            if (gurk.deadGurk()) { // if the gurkin is dead
                this.shotResults.setKill(coords); // set the shot results to a kill
                for (int i = 0; i < gurk.getSize(); i++) {
                    if ((new Coordinates(coords.getX() + i, coords.getY()).validCoords()) && board.getTile(new Coordinates(coords.getX() + i, coords.getY())).check(gurk)) {
                        this.shotResults.setKill(new Coordinates(coords.getX() + i, coords.getY())); // set the shot results to a kill
                    } if ((new Coordinates(coords.getX() , coords.getY() + i).validCoords()) && board.getTile(new Coordinates(coords.getX() , coords.getY() + i)).check(gurk)) {
                        this.shotResults.setKill(new Coordinates(coords.getX() , coords.getY() + i));
                    } if ((new Coordinates(coords.getX() - i, coords.getY()).validCoords()) && board.getTile(new Coordinates(coords.getX() - i, coords.getY())).check(gurk)) {
                        this.shotResults.setKill(new Coordinates(coords.getX() - i, coords.getY()));
                    } if ((new Coordinates(coords.getX(), coords.getY() - i).validCoords()) && board.getTile(new Coordinates(coords.getX(), coords.getY() - i)).check(gurk)) {
                        this.shotResults.setKill(new Coordinates(coords.getX(), coords.getY() - i));
                    }
                }
                if (!(gurk instanceof Terrain)) this.remaining_gurkins --;// decrement the number of gurkins remaining
                displayKillGIF(gurk);
            } else {
                changeTurn();
            }

        } else if (result.equals("miss")) { // if the shot was a miss
            this.shotResults.setMiss(coords);// set the shot results to a miss
            changeTurn(); // tells the view to carry out change turn stuff

        }
    }

    @Override
    public void changeTurn() {
        Turn.changeTurn();
        notifyTurnObservers();
    }

    public void notifyTurnObservers() {
        for (PlayerAttackObserver observer : attackObservers) {
            observer.changeTurn();}}

    public boolean checkWin() { // checks if the player has won
        if (this.remaining_gurkins == 0) {
            return true;
        }
        return false;
    }
    @Override
    public void displayKillGIF(Gurkin gurk) {
        for (PlayerAttackObserver observer : attackObservers) {
            observer.displayKillGIF(gurk);}}


    // checks if the gurkin can be placed at the given coordinates
    // places a gurkin on the board if it can be placed
    public Boolean validGurkinSetup(Gurkin gurk, Direction.direction direction, Coordinates cords) {
        boolean valid = cords.validCoords(direction, gurk, gurkinBoard);
        if (valid && remaining_gurkins < 5) {
            gurkinBoard.placeGurkin(cords, direction, gurk);
            for (int i = 0; i < unplacedGurks.size(); i++) {
               if (unplacedGurks.get(i).getClass().equals(gurk.getClass())) {
                   unplacedGurks.remove(i);
                   break;
               }
            }
            updateSidePanel(unplacedGurks);
            this.remaining_gurkins ++;
        }
        if (remaining_gurkins >= 5) {
            finalisePlacement();
        }
        return valid;
    }


    @Override
    public void updateSidePanel(ArrayList<Gurkin> gurks) {
        notifyPlacement(gurks);
    }

    public void notifyPlacement(ArrayList<Gurkin> gurks) {
        for (PlayerObserver observer : playerObservers) {
            observer.updateSidePanel(gurks);}}

    public void registerObserver(PlayerObserver observer) {
        playerObservers.add(observer);
    }

    public void registerAttackObserver(PlayerAttackObserver observer) {
        attackObservers.add(observer);
    }

    public void finalisePlacement() {
        for (PlayerObserver observer : playerObservers) {
            observer.finalisePlacement();}}

    public void resetPlacement() {
        this.remaining_gurkins = 0;
        this.gurkinBoard = new Board();
        this.unplacedGurks = new ArrayList<Gurkin>();
        unplacedGurks.add(new Pickle());
        unplacedGurks.add(new Gherkin());
        unplacedGurks.add(new Conichon());
        unplacedGurks.add(new Yardlong());
        unplacedGurks.add(new Zuchinni());
    }

    public ShotResults getResultBoard() {
        return shotResults;
    }

    public int getId () {
        return this.id;
    }
    public void setId(int id) {this.id = id;}

    public Boolean getCurrentPlayer() {return CurrentPlayer;}

    public void setDifficultyString(String difficultyString) {
        this.difficultyString = difficultyString;
    }
    public String getDifficultyString() {return this.difficultyString;}

    public Boolean getCurrent() {
        return this.CurrentPlayer;
    }
    public ArrayList<Gurkin> getUnplacedGurks() {
        return unplacedGurks;
    }

}

