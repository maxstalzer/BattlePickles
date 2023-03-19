package Base;

public class Board {
    private Tile[][] BoardArr;

    // Initialize board
    public Board() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                BoardArr[i][j] = new Tile();
            }
        }
    }
    public Tile getTile(Coordinates a) {
        return BoardArr[a.getCoordinates()[0]][a.getCoordinates()[1]];
    }
    public void putGurken(String a,Coordinates[] b) {
        for (int i = 0; i < b.length; i++) {
            getTile(b[i]).setGurkin(a);
        }
    }
    public String attack(Coordinates a) {
        if (!getTile(a).isHit()) {
            getTile(a).hitTile();
            if (getTile(a).hasGurkin()) {
                getTile(a).getGurkin().decrementLives()
               return "hit";
            } else {
                return "miss";
            }
        } else {
            return "noob";
        }

    }
}

