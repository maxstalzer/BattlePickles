package Base.Gurkins;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Conichon")
public class Conichon extends  Gurkin {
    public Conichon(){
        super(2);
    }
    public Character toChar() {
        return 'c';
    }


}