package Base;

import java.util.Scanner;
public class Placement {
    static boolean b;
    enum Direction {
        Horizontal,
        Vertical
    }

    public static void gurkPlacer(String[] gurkIDs, Player p) {
        Scanner sc = new Scanner(System.in);
        for (String gurkid: gurkIDs) {
            System.out.println("Choosing placement of " + gurkid);
            System.out.println("Enter x coordinates and direction");
            int x = sc.nextInt();
            int y = sc.nextInt();
            String direction_string = sc.next();
            Direction direction = null;
            if (direction_string.equals("Horizontal")) {
                direction = Direction.Horizontal;
            }
            else {
                direction = Direction.Vertical;
            }
            Coordinates Cors = new Coordinates(x,y);
            Gurkin gurk = chooseGurkin(Cors, direction, gurkid);
            placeGurken(p.gurkinBoard, gurk);
        }
    }

    public static Gurkin chooseGurkin(Coordinates start, Direction dir, String gurkinID) {

        if (gurkinID == "Zuchinni"){
            Gurkin gurk = new Zuchinni(start, dir);
            return gurk;
        }
        else if (gurkinID == "Gherkin"){
            Gurkin gurk = new Gherkin(start, dir);
            return gurk;
        }
        else if (gurkinID == "Pickle"){
           Gurkin gurk = new Pickle(start, dir);
           return gurk;
        }
        else if (gurkinID == "Conichon"){
           Gurkin gurk = new Conichon(start, dir);
           return gurk;
        }
        else if (gurkinID == "Yardlong"){
           Gurkin gurk = new Yardlong(start, dir);
           return gurk;
        }
        return null;

    }

    public static boolean validate(Board B, Gurkin gurk) {
        int length = gurk.getSize();
        Coordinates[] coords = gurk.coordinates;

        for (Coordinates c : coords) {
            if (c.getX() > 9 || c.getX()<0 || c.getY() > 9 || c.getY()<0) {
                return false;
            }
            else if (B.getTile(c).hasGurkin() == true) {
                return false;
            }
        }
        return true;
    }

    public static void updateBoard(Board b, Gurkin g) {

        // Iterating through the coordinates of the gurkin
        for (Coordinates coordinate : g.coordinates) {
            // Setting the tile that corresponds to the coordinate to the gurkin
            b.getTile(coordinate).setGurkin(g);
        }
    }


    // Takes a given board, starting coordinate and direction and uses validate board method and then updates the board
    public static void placeGurken(Board B, Gurkin gurk) {

        // Validating the placement
        b = validate(B, gurk);

        // While loop that keeps going till valid placement
        while (!b) {
            //We rerun the program to get new coordinates (need to be added)
            b = validate(B, gurk);
        }
        // Updating the board matrix with the fresh gurk
        updateBoard(B, gurk);
    }
}
