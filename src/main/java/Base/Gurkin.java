package Base;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Gurkin")
public abstract class Gurkin {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int size;
    @DatabaseField
    private int lives;


//  Constructor for the class
    Gurkin(int size) {
        this.size = size;
        this.lives = size;
    }
    public Gurkin() {}
    public int getSize() {
        return this.size;
    }
    public void decrementLives() {
        this.lives --;
    }

    public boolean deadGurk() {
        return this.lives < 1;
    }

//  Method to return the string representation of each gurkin
     abstract Character toChar();

}