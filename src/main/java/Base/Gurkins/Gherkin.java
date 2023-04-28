package Base.Gurkins;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Gherkin")
public class Gherkin extends Gurkin {

    public Gherkin() {
        super(3);
    }
    public Character toChar() {
        return 'g';
    }

}
