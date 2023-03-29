package Base;

public class Yardlong extends Gurkin {
    public Yardlong(Coordinates start, Placement.Direction direction) {
        super(5, "Yardlong", start, direction);
    }
    public Character toChar() {
        return 'y';
    }
}