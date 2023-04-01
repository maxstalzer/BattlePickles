package Base;

public class Board {
    private Tile[][] BoardArr = new Tile[10][10];

    // Initialize board
    public Board() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                BoardArr[i][j] = new Tile();
            }
        }
    }
    public Tile getTile(Coordinates a) {
        return BoardArr[a.getX()][a.getY()];
    }


    public String attack(Coordinates a) {
        if (!getTile(a).isHit()) {
            getTile(a).hitTile();
            if (getTile(a).hasGurkin()) {
                getTile(a).getGurkin().decrementLives();
               return "hit";
            } else {
                return "miss";
            }
        } else {
            return "noob";
        }

    }

    public void displayBoard() {
        System.out.println("+---------------------+");
        System.out.println("  0 1 2 3 4 5 6 7 8 9 ");
        for (int i = 0; i < 10; i++) {
            System.out.print("| ");
            for (int j = 0; j < 10; j++) {
                System.out.print( BoardArr[j][i].toChar() + " ");
            }
            System.out.println(" |");
        }
        System.out.println("  0 1 2 3 4 5 6 7 8 9 ");
        System.out.println("+---------------------+");

    }

    public void setupBoard(Gurkin g, Direction.direction dir, Coordinates startCoor) {

        for (int i = 0; i < g.getSize(); i++) {
            if (dir.equals(Direction.direction.Horizontal)) {
                getTile(new Coordinates(startCoor.getX() + i, startCoor.getY())).setGurkin(g);
            } else {
                getTile(new Coordinates(startCoor.getX(), startCoor.getY() + i)).setGurkin(g);
            }
        }
    }
}

