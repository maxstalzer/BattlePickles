package Base.Gurkins;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Zuchinni")
public class Zuchinni extends Gurkin {
    public Zuchinni() {
        super(4);
    }

    public Character toChar() {
        return 'z';
    }
}
