package Base;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;

@DatabaseTable(tableName = "Conichon")
public class Conichon extends  Gurkin {

    @ForeignCollectionField(columnName = "Tiles")
    private Collection<Tile> tiles = new ArrayList<>();
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private Board board;
    public Conichon(){
        super(2);
    }
    public Character toChar() {
        return 'c';
    }

    public void setBoard(Board board) {this.board = board;}

    public void addTile(Tile tile) {this.tiles.add(tile);}


}