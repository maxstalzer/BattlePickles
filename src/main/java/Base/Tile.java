package Base;

import Base.Gurkins.Gurkin;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Tile")
public class Tile {
    @DatabaseField(canBeNull = false)
    private boolean gurkin; // If there is a gurkin on the tile
    @DatabaseField(columnName = "gurkin_id", foreign = true,foreignAutoRefresh = true)
    private Gurkin gurkinID; // The gurkin on the tile
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

    public Board getBoard() {
        return this.board;
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


    public Boolean check(Gurkin gurk) {
        if (!hasGurkin()) return false;
        return gurkinID.equals(gurk);
    }


}
