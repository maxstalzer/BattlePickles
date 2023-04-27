package Base;

public class GurkTuple<Gurkin, Coordinates, Direction> {
    private Gurkin gurkin;
    private Coordinates coords;
    private Direction direction;

    public GurkTuple(Gurkin gurkin, Coordinates coords, Direction direction) {
        this.gurkin = gurkin;
        this.coords = coords;
        this.direction = direction;
    }

    public Gurkin getGurkin() {
        return gurkin;
    }

    public Coordinates getCoords() {
        return coords;
    }

    public Direction getDirection() {
        return direction;
    }
}
