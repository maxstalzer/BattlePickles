package Base;

import Base.Gurkins.Gurkin;

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

    public boolean validCoords(Direction.direction dir, Gurkin gurk, Board board) {
        for (int i = 0; i < gurk.getSize(); i++) {
            if (dir.equals(Direction.direction.Horizontal) && ((X + i > 9 || X < 0 || Y > 9 || Y < 0) || board.getTile(new Coordinates(X+i, Y)).hasGurkin())) {
                return false;
            } else if (dir.equals(Direction.direction.Vertical) && ((X > 9 || X < 0 || Y + i > 9 || Y < 0) || board.getTile(new Coordinates(X, Y + i)).hasGurkin())) {
                return false;
            }
        }
        return true;
    }

    public void updateCoords(int x, int y) {
        this.X = x;
        this.Y = y;
    }
}
