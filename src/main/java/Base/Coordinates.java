package Base;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Coordinates")
public class Coordinates {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int X; // X coordinate
    @DatabaseField
    private int Y; // Y coordinate
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private Board board;

    public Coordinates(int x, int y) { // Constructor
        this.X = x;
        this.Y = y;
    }
    public Coordinates () {}
    public int getX() {
        return X;
    } // Getters

    public int getY() {
        return Y;
    } // Getters

    public int getId() {return id;}
    public Board getBoard() {return board;}

    public void setX(int x) {this.X = x;}
    public void setY(int y) {this.Y = y;}
    public void setId(int id) {this.id = id;}
    public void setBoard(Board board) {this.board = board;}

    public boolean validCoords () { // Check if coordinates are valid
        if (X > 9 || X < 0 || Y > 9 || Y < 0) {
            return false;
        }
        return true;
    }

    public boolean validCoords(Direction.direction dir, Gurkin gurk, Board board) { // Check if coordinates are valid for a gurkin
        for (int i = 0; i < gurk.getSize(); i++) {
            if (dir.equals(Direction.direction.Horizontal) && ((X + i > 9 || X < 0 || Y > 9 || Y < 0) || board.getTile(new Coordinates(X+i, Y)).hasGurkin())) {
                return false;
            } else if (dir.equals(Direction.direction.Vertical) && ((X > 9 || X < 0 || Y + i > 9 || Y < 0) || board.getTile(new Coordinates(X, Y + i)).hasGurkin())) {
                return false;
            }
        }
        return true;
    }

//    public void updateCoords(int x, int y) { // Update coordinates
//        this.X = x;
//        this.Y = y;
//    }

}
