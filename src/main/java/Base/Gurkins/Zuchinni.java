package Base.Gurkins;

import Base.Tile;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

@DatabaseTable(tableName = "Zuchinni")
public class Zuchinni extends Gurkin {
    @ForeignCollectionField(columnName = "Tiles",eager = true, foreignFieldName = "gurkinID")
    private Collection<Tile> tiles;
    public Zuchinni() {
        super(4);
    }

    public Character toChar() {
        return 'z';
    }
}
