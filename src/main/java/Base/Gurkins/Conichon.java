package Base.Gurkins;

import Base.Tile;

import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

@DatabaseTable(tableName = "Conichon")
public class Conichon extends  Gurkin {
    @ForeignCollectionField(columnName = "Tiles", eager = true, foreignFieldName = "gurkinID_id")
    private Collection<Tile> tiles;
    public Conichon(){
        super(2);
    }
    public Character toChar() {
        return 'c';
    }


}