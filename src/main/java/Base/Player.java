package Base;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import io.cucumber.java.it.Data;

import java.util.Objects;
import java.util.Scanner;
@DatabaseTable(tableName = "Player")
public class  Player {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    String name;
    @DatabaseField(canBeNull = false)
    int remaining_gurkins;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    Board gurkinBoard;

    private Boolean CurrentPlayer;

    public Player (int id, String name, Boolean CurrentPlayer) {
        this.id = id;
        this.name = name;
        this.CurrentPlayer = CurrentPlayer;
    }

    public void setCurrentPlayer(Boolean CurrentPlayer) {
        this.CurrentPlayer = CurrentPlayer;
    }

    public int getId (){
        return this.id;
    }

    public Character[][] shotResults = new Character[10][10];

    public Board getGurkinBoard() {
        return gurkinBoard;
    }

    public Player() {

//      Setup the gurkin board
        this.gurkinBoard = new Board(this);
        this.remaining_gurkins = 5;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setupBoard() {
        //      Place gurkins on the players board
        gurkinSetup(new String[]{"Gherkin", "Zuchinni","Pickle", "Conichon", "Yardlong" });
    }

    private void displayShotBoard() {
        System.out.println("+---------------------+");
        System.out.println("  0 1 2 3 4 5 6 7 8 9 ");
        for (int i = 0; i < 10; i++) {
            System.out.print("| ");
            for (int j = 0; j < 10; j++) {
                if (shotResults[j][i] == null) {
                    System.out.print("  ");
                } else {
                    System.out.print( shotResults[j][i] + " ");
                }
            }
            System.out.println(" |");
        }
        System.out.println("  0 1 2 3 4 5 6 7 8 9 ");
        System.out.println("+---------------------+");

    }

    public void attack_round(Board board) {
        displayShotBoard();
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        int y = sc.nextInt();

        Coordinates coords = new Coordinates(x, y);
        boolean validEntry = coords.validCoords();
        while (!validEntry) {
            System.out.println("Please enter new x and y coordinates");
            int xPrime = sc.nextInt();
            int yPrime = sc.nextInt();
            coords.updateCoords(xPrime, yPrime);
            validEntry = coords.validCoords();
        }

        shoot(board, coords);

    }

//  Allows a player to shoot at given coordinates on the opposing player's board
    public void shoot(Board board, Coordinates coords) {
        String result = board.attack(coords);
        int x = coords.getX();
        int y = coords.getY();

//      if the player successfully hits an opponents pickle the player's shots
//      board is updated with x for hit, o for miss and the whole pickle is
//      updated with k
        if (result.equals("hit")) {
            this.shotResults[x][y] = 'x';
            System.out.println("Hit!");
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
                System.out.println("You killed a gurkin");
                this.remaining_gurkins --;
            }
        } else if (result.equals("miss")) {
            this.shotResults[x][y] = 'o';
            System.out.println("Miss lol");
        } else if (result.equals("noob")) {
            System.out.println("Already hit here");
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
            gurkinBoard.setupBoard(gurk, direction, startCors);

        }
        System.out.println("Placement complete! Final Board layout is:");
        gurkinBoard.displayBoard();
    }

}

