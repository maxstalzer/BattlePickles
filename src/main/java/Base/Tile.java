package Base;

import Base.Gurkins.*;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;

@DatabaseTable(tableName = "Tile")
public class Tile {
    @DatabaseField(canBeNull = false)
    private boolean gurkin; // If there is a gurkin on the tile
    @DatabaseField(foreign = true,foreignAutoRefresh = true,foreignColumnName = "id")
    private Gurkin gurkinID; // The gurkin on the tile
    @DatabaseField
    private String gurkinChar;
    @DatabaseField(canBeNull = false)
    private boolean isHit = false; // If the tile has been hit

    //added a bunch of variables and methods for online to work besides the @database ads -V
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private int x;
    @DatabaseField(canBeNull = false)
    private int y;

    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private Board board;

    public Tile() {}

    public int getX(){
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getId() {
        return id;
    }
    public void setBoard(Board b) {
        this.board = b;
    }
    public int getBoardId() {
        return board.getId();
    }

    //until here -V

    public boolean hasGurkin() {
        return this.gurkin;
    } // Getters
    public void setGurkin(Gurkin x) { // Set gurkin on tile
        this.gurkin = true;
        this.gurkinID = x;
    }
    public void hitTile() {this.isHit = true;} // Hit the tile
    public Gurkin getGurkin() {
        return gurkinID;
    } // Get the gurkin on the tile
    public boolean isHit() {
        return this.isHit;
    } // Check if the tile has been hit
    public void setChar(Gurkin gurkin) {this.gurkinChar = Character.toString(gurkinID.toChar());}
    public Character toChar() { // Convert tile to char
        if (hasGurkin()) {
            return gurkinID.toChar();
        }
        return ' ';
    }

    public Boolean check(Gurkin gurk) {
        if (!hasGurkin()) return false;
        return gurkinID.equals(gurk);
    }

    public Tile deepClone() {
        Tile clone = new Tile();
        clone.gurkin = this.gurkin;
        clone.gurkinID = this.gurkinID;
        clone.isHit = this.isHit;
        return clone;
    }
}
