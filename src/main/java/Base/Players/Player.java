package Base.Players;


import Base.Board;
import Base.Coordinates;
import Base.Direction;
import Base.Gurkins.*;
import Base.Turn;

public class  Player {
    String name; // The name of the player
    int remaining_gurkins; //   The number of gurkins remaining to be shot

    Board gurkinBoard; // The board that the player uses to place their gurkins

    String turnID; // stores if it's player 1 or 2 (unknown)

    private Character[][] shotResults = new Character[10][10]; // Stores results of shots

    public Board getGurkinBoard() {
        return gurkinBoard;
    } // the board of gurkins

    public Player() { // Constructor

//      Setup the gurkin board
        this.gurkinBoard = new Board();
        this.remaining_gurkins = 0;
        turnID = Turn.getTurn();
    }


    public Character[][] getShotResults() {
        return shotResults;
    } // Returns shot results

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
        String result = board.attack(coords);
        int x = coords.getX();
        int y = coords.getY();
        if (result.equals("hit")) { // if the shot was a hit
            this.shotResults[x][y] = 'x';
            Turn.changeTurn(); // change turn
            Gurkin gurk = board.getTile(coords).getGurkin(); // get the gurkin that was hit
            if (gurk.deadGurk()) { // if the gurkin is dead
                this.shotResults[coords.getX()][coords.getY()] = 'k';
                for (int i = 0; i < gurk.getSize(); i++) {
                    if ((new Coordinates(coords.getX() + i, coords.getY()).validCoords()) && board.getTile(new Coordinates(coords.getX() + i, coords.getY())).check(gurk)) {
                        this.shotResults[coords.getX()+i][coords.getY()] = 'k';
                    } else if ((new Coordinates(coords.getX() , coords.getY() + i).validCoords()) && board.getTile(new Coordinates(coords.getX() , coords.getY() + i)).check(gurk)) {
                        this.shotResults[coords.getX()][coords.getY() + i] = 'k';
                    } else if ((new Coordinates(coords.getX() - i, coords.getY()).validCoords()) && board.getTile(new Coordinates(coords.getX() - i, coords.getY())).check(gurk)) {
                        this.shotResults[coords.getX() - i][coords.getY()] = 'k';

                    } else if ((new Coordinates(coords.getX(), coords.getY() - i).validCoords()) &&board.getTile(new Coordinates(coords.getX(), coords.getY() - i)).check(gurk)) {
                        this.shotResults[coords.getX()][coords.getY() - i] = 'k';
                    }
                }
                this.remaining_gurkins --; // decrement the number of gurkins remaining
            }
        } else if (result.equals("miss")) { // if the shot was a miss
            this.shotResults[x][y] = 'o';
            Turn.changeTurn();
        }
    }
    public boolean checkWin() { // checks if the player has won
        if (this.remaining_gurkins == 0) {

            return true;
        }
        return false;
    }


    // checks if the gurkin can be placed at the given coordinates
    // places a gurkin on the board if it can be placed
    public Boolean validGurkinSetup(Gurkin gurk, Direction.direction direction, Coordinates cords) {
        boolean valid = cords.validCoords(direction, gurk, gurkinBoard);
        if (valid) {
            gurkinBoard.placeGurkin(gurk, direction, cords);
            this.remaining_gurkins ++;

        }
        if (!(remaining_gurkins < 5)) {
            Turn.changeTurn();
        }
        return valid;
    }

    public Player deepClone() { // deep clones the player
        Player copy = new Player();
        copy.name = name;
        copy.remaining_gurkins = remaining_gurkins;
        copy.gurkinBoard = gurkinBoard.deepClone();
        copy.shotResults = shotResults;
        copy.turnID = turnID;
        return copy;
    }
}

