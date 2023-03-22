package Base;

public class Player {
    String name;
    int remaining_gurkins = 5;

    Board gurkinBoard;

    Character[][] shotResults = new Character[10][10];

    public Board getGurkinBoard() {
        return gurkinBoard;
    }

    public Player(String name) {
        this.name = name;
        this.gurkinBoard = new Board();
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
            Gurkin gurk = board.getTile(coords).getGurkin();
            if (gurk.deadGurk()) {
                for (int i = 0; i < gurk.getSize(); i++) {
                   int xCor = gurk.getGurkinCoors()[i].getX();
                   int yCor = gurk.getGurkinCoors()[i].getY();
                   this.shotResults[xCor][yCor] = 'k';
                }
            }
        } else if (result.equals("miss")) {
            this.shotResults[x][y] = 'o';
        } else {
            // Invoke some kind of error message here
        }
    }
}

