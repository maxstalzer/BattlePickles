package Base;

public class Player {
    String name;
    int gurkins = 5;

    public Board gurkinBoard;


    public Player(String name) {
        this.name = name;
        this.gurkinBoard = new Board();
    }
    public void shoot(Board board, Coordinates coords) {
        boolean niceHit = board.attack(coords);
    }

}
