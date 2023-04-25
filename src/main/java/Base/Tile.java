package Base;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Tile")
public class Tile {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    int X;
    @DatabaseField
    int Y;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "board_id")
    private Board board;

    @DatabaseField(canBeNull = false)
    private boolean gurkin;
    @DatabaseField(columnName = "gurkin_id", foreign = true, foreignAutoRefresh = true)
    private Gurkin gurkinID;
    @DatabaseField(canBeNull = false)
    private boolean isHit = false;

    public void setX (int i) {
        X = i;
    }
    public void setY (int i) {
        Y = i;
    }
    public Tile () {}

    public int getX() {
        return X;
    }
    public int getY() {
        return Y;
    }

    public void setBoard(Board b) {
        board = b;
    }
    public boolean hasGurkin() {
        return this.gurkin;
    }
    public void setGurkin(Gurkin x) {
        this.gurkin = true;
        this.gurkinID = x;
    }
    public void hitTile() {this.isHit = true;}
    public Gurkin getGurkin() {
        return gurkinID;
    }
    public boolean isHit() {
        return this.isHit;
    }

    public Character toChar() {
        if (hasGurkin()) {
            return gurkinID.toChar();
        }
        return ' ';
    }

    public Boolean check(Gurkin gurk) {
        if (!hasGurkin()) return false;
        return gurkinID.equals(gurk);
    }
}
