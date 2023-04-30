package Base.Gurkins;

import Base.Board;
import Base.Tile;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;

@DatabaseTable(tableName = "Zuchinni")
public class Zuchinni extends Gurkin {
    @ForeignCollectionField(columnName = "Tiles")
    private Collection<Tile> tiles = new ArrayList<>();
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private Board board;
    public Zuchinni() {
        super(4);
    }

    public Character toChar() {
        return 'z';
    }
    public void setBoard(Board board) {this.board = board;}
    public void addTile(Tile tile) {this.tiles.add(tile);}
}
