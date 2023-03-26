package Base;

public class Coordinates {

    private int X;
    private int Y;

    public Coordinates(int x, int y) {
        this.X = x;
        this.Y = y;
    }
    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public boolean validCoords () {
        if (X > 9 || X < 0 || Y > 9 || Y < 0) {
            return false;
        }
        return true;
    }

    public void updateCoords(int x, int y) {
        this.X = x;
        this.Y = y;
    }
}
