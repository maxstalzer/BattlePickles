package Base.Gurkins;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Gurkin")
public abstract class Gurkin {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private final int size;
    @DatabaseField
    private int lives;


//  Constructor for the class
    Gurkin(int size) {
        this.size = size;
        this.lives = size;


    }
    public int getSize() {
        return this.size;
    }

    public int getLives() {
        return lives;
    }

    public void decrementLives() {
        this.lives --;
    }

    public boolean deadGurk() {
        return this.lives < 1;
    }

//  Method to return the string representation of each gurkin
     public abstract Character toChar();

}