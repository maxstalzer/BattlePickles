package Base;

import Base.Gurkins.Gurkin;

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

    public void placeGurkin(Gurkin g, Direction.direction dir, Coordinates startCoor) {

        for (int i = 0; i < g.getSize(); i++) {
            if (dir.equals(Direction.direction.Horizontal)) {
                getTile(new Coordinates(startCoor.getX() + i, startCoor.getY())).setGurkin(g);
            } else {
                getTile(new Coordinates(startCoor.getX(), startCoor.getY() + i)).setGurkin(g);
            }
        }
    }

    public Board deepClone() {
        Board clone = new Board();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                clone.BoardArr[i][j] = this.BoardArr[i][j].deepClone();
            }
        }
        return clone;
    }
}

