package Base;


import Base.Gurkins.*;
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
        this.remaining_gurkins = 5;
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

//    TODO: controller
    public void setupBoard() {
        //      Place gurkins on the players board
        gurkinSetup(new String[]{"Gherkin", "Zuchinni","Pickle", "Conichon", "Yardlong" });
    }


//    TODO: controller
    public void attack_round(Board board, Coordinates coords) {
        boolean validEntry = coords.validCoords();
        while (!validEntry) {
            Coordinates cPrime = getAttackInfo(this);
            coords.updateCoords(cPrime.getX(), cPrime.getY());
            validEntry = coords.validCoords();
        }

        shoot(board, coords);

    }

//  Allows a player to shoot at given coordinates on the opposing player's board

//    TODO: make sure that this method returns a string with the result of the shot
    public void shoot(Board board, Coordinates coords) {
        String result = board.attack(coords);
        int x = coords.getX();
        int y = coords.getY();

//      if the player successfully hits an opponents pickle the player's shots
//      board is updated with x for hit, o for miss and the whole pickle is
//      updated with k
        if (result.equals("hit")) {
            this.shotResults[x][y] = 'x';
//            System.out.println("Hit!");
            Gurkin gurk = board.getTile(coords).getGurkin();
            if (gurk.deadGurk()) {
                this.shotResults[coords.getX()][coords.getY()] = 'k';
                for (int i = 0; i < gurk.getSize(); i++) {
                    if (board.getTile(new Coordinates(coords.getX() + i, coords.getY())).check(gurk)) {
                        this.shotResults[coords.getX()+i][coords.getY()] = 'k';
                    } else if (board.getTile(new Coordinates(coords.getX() , coords.getY() + i)).check(gurk)) {
                        this.shotResults[coords.getX()][coords.getY() + i] = 'k';
                    } else if (board.getTile(new Coordinates(coords.getX() - i, coords.getY())).check(gurk)) {
                        this.shotResults[coords.getX() - i][coords.getY()] = 'k';

                    } else if (board.getTile(new Coordinates(coords.getX(), coords.getY() - i)).check(gurk)) {
                        this.shotResults[coords.getX()][coords.getY() - i] = 'k';
                    }

                }
//                System.out.println("You killed a gurkin");
                this.remaining_gurkins --;
            }
        } else if (result.equals("miss")) {
            this.shotResults[x][y] = 'o';
//            System.out.println("Miss lol");
        } else if (result.equals("noob")) {
//            System.out.println("Already hit here");
            Boolean validEntry = coords.validCoords();

            while (!validEntry) {
                System.out.println("Please enter new x and y coordinates");
                Scanner sc = new Scanner(System.in);
                int xPrime = sc.nextInt();
                int yPrime = sc.nextInt();
                coords.updateCoords(xPrime, yPrime);
                validEntry = coords.validCoords();
            }
            shoot(board, coords);
        }
    }
    public boolean checkWin() {
        if (this.remaining_gurkins == 0) {
            System.out.println(this.name + " wins the game!");
            return true;
        }
        return false;
    }
    private Gurkin chooseGurkin (String gurkinID) {
        if (Objects.equals(gurkinID, "Zuchinni")){
            return new Zuchinni();
        }
        else if (Objects.equals(gurkinID, "Gherkin")){
            return new Gherkin();
        }
        else if (Objects.equals(gurkinID, "Pickle")){
            return new Pickle();
        }
        else if (Objects.equals(gurkinID, "Conichon")){
            return new Conichon();
        }
        else if (Objects.equals(gurkinID, "Yardlong")){
            return new Yardlong();
        }
        return null;
    }


//    TODO: To be moved to controller?
    private void gurkinSetup(String [] gurkIDs) {
        Scanner sc = new Scanner(System.in);
        for (String gurkID: gurkIDs) {
            gurkinBoard.displayBoard();

            // First we create the gurkin object
            Gurkin gurk = chooseGurkin(gurkID);


            System.out.println("Choosing placement of " + gurkID);
            System.out.println("Enter Coordinates and direction separated by a space:");
            int x = sc.nextInt();
            int y = sc.nextInt();
            String direction_string = sc.next();
            Direction.direction direction;
            if (direction_string.equals("Horizontal")) {
                direction = Direction.direction.Horizontal;
            } else {
                direction = Direction.direction.Vertical;
            }
            Coordinates startCors = new Coordinates(x, y);

//          Validate player entered Coordinates
            Boolean valid = startCors.validCoords(direction, gurk, gurkinBoard);

            while (!valid) {
                System.out.println("Please enter valid coordinates and direction:");
                x = sc.nextInt();
                y = sc.nextInt();
                direction_string = sc.next();
                if (direction_string.equals("Horizontal")) {
                    direction = Direction.direction.Horizontal;
                } else {
                    direction = Direction.direction.Vertical;
                }
                startCors.updateCoords(x, y);
                valid = startCors.validCoords(direction, gurk, gurkinBoard);
            }

            // update the board with the validated gurkin coordinates
            gurkinBoard.placeGurkin(gurk, direction, startCors);

        }
        System.out.println("Placement complete! Final Board layout is:");
        gurkinBoard.displayBoard();
    }

}

