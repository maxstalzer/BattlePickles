package Base;

public class Player {
    String name;
    int gurkins = 5;

    Board gurkinBoard;

    Character[][] shotResults = new Character[10][10];

    public Board getGurkinBoard() {
        return gurkinBoard;
    }

    public Player(String name) {
        this.name = name;
        this.gurkinBoard = new Board();
    }
    public void shoot(Board board, Coordinates coords) {
        String result = board.attack(coords);
        int x = coords.getCoordinates()[0];
        int y = coords.getCoordinates()[1];

        if (result.equals("hit")) {
            this.shotResults[x][y] = 'x';
        } else if (result.equals("miss")) {
            this.shotResults[x][y] = 'o';
        }
    }

}
