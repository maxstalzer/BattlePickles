package Base.Gurkins;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Yardlong")
public class Yardlong extends Gurkin {
    public Yardlong() {
        super(5);
    }
    public Character toChar() {
        return 'y';
    }
}