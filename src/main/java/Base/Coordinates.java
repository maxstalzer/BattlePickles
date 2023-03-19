package Base;

public class Coordinates {

    private int X;
    private int Y;

    public Coordinates(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    public int[] getCoordinates() {
        return new int[] {this.X, this.Y};
    }

}
