package Base;

import java.util.Objects;
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
            p.getGurkinBoard().displayBoard();
            System.out.println("Choosing placement of " + gurkid);
            System.out.println("Enter x coordinates and direction");
            int x = sc.nextInt();
            int y = sc.nextInt();
            String direction_string = sc.next();
            Direction direction;
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

        if (Objects.equals(gurkinID, "Zuchinni")){
            return new Zuchinni(start, dir);
        }
        else if (Objects.equals(gurkinID, "Gherkin")){
            return new Gherkin(start, dir);
        }
        else if (Objects.equals(gurkinID, "Pickle")){
            return new Pickle(start, dir);
        }
        else if (Objects.equals(gurkinID, "Conichon")){
            return new Conichon(start, dir);
        }
        else if (Objects.equals(gurkinID, "Yardlong")){
            return new Yardlong(start, dir);
        }
        return null;

    }

    public static boolean validate(Board B, Gurkin gurk) {
        Coordinates[] coords = gurk.coordinates;

        for (Coordinates c : coords) {
            if (c.getX() > 9 || c.getX()<0 || c.getY() > 9 || c.getY()<0) {
                return false;
            }
            else if (B.getTile(c).hasGurkin()) {
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
            Scanner sc = new Scanner(System.in);

            System.out.print("Please enter valid coordinates and Direction: ");
            int x = sc.nextInt();
            int y = sc.nextInt();
            String direction_string = sc.next();
            Direction direction;
            if (direction_string.equals("Horizontal")) {
                direction = Direction.Horizontal;
            }
            else {
                direction = Direction.Vertical;
            }
            Coordinates coors = new Coordinates(x, y);
            gurk.setCoordinates(coors, direction);
            b = validate(B, gurk);
        }
        // Updating the board matrix with the fresh gurk
        updateBoard(B, gurk);
    }
}
