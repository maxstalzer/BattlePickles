package Base.Players;


import Base.Board;
import Base.Coordinates;
import Base.Direction;
import Base.Gurkins.*;
import Base.Turn;
import TempView.ConsoleUI;

import java.util.Objects;
import java.util.Scanner;

public class  Player {
    String name;
    int remaining_gurkins;

    Board gurkinBoard;

    private Character[][] shotResults = new Character[10][10];

    public Board getGurkinBoard() {
        return gurkinBoard;
    }

    public Player() {

//      Setup the gurkin board
        this.gurkinBoard = new Board();
        this.remaining_gurkins = 0;
    }


    public Character[][] getShotResults() {
        return shotResults;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getRemaining_gurkins() {return remaining_gurkins; }

    public void setRemaining_gurkins(int val) {remaining_gurkins = val;}


//  Allows a player to shoot at given coordinates on the opposing player's board

    public void shoot(Board board, Coordinates coords) {
        String result = board.attack(coords);
        int x = coords.getX();
        int y = coords.getY();
        if (result.equals("hit")) {
            this.shotResults[x][y] = 'x';
            Turn.changeTurn();
            Gurkin gurk = board.getTile(coords).getGurkin();
            if (gurk.deadGurk()) {
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
                this.remaining_gurkins --;
            }
        } else if (result.equals("miss")) {
            this.shotResults[x][y] = 'o';
            Turn.changeTurn();
        }
    }
    public boolean checkWin() {
        if (this.remaining_gurkins == 0) {

            return true;
        }
        return false;
    }
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
}

