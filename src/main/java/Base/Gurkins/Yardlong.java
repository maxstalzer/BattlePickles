package Base.Gurkins;

import Base.Tile;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

@DatabaseTable(tableName = "Yardlong")
public class Yardlong extends Gurkin {
    @ForeignCollectionField(columnName = "Tiles",eager = true, foreignFieldName = "gurkinID")
    private Collection<Tile> tiles;
    public Yardlong() {
        super(5);
    }
    public Character toChar() {
        return 'y';
    }
}