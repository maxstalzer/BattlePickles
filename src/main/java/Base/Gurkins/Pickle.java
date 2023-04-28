package Base.Gurkins;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Pickle")
public class Pickle extends Gurkin {

    public Pickle() {
        super(3);
    }

    public Character toChar() {
        return 'p';
    }
}