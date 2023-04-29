package Base.Gurkins;

import Base.Board;
import Base.Tile;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;


public abstract class Gurkin {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int size;
    @DatabaseField
    private int lives;
    private Board board;
    private Collection<Tile> tiles;

//  Constructor for the class
    Gurkin(int size) {
        this.size = size;
        this.lives = size;
    }

    public Gurkin() {}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return this.size;
    }
    public void setSize(int size) {this.size = size;}

    public int getLives() {
        return lives;
    }
    public void setLives(int lives) {this.lives = lives;}

    public void decrementLives() {
        this.lives --;
    }

    public boolean deadGurk() {
        return this.lives < 1;
    }

//  Method to return the string representation of each gurkin
     public abstract Character toChar();
    public void setBoard(Board board) {this.board = board;}
    public void addTile(Tile tile) {this.tiles.add(tile);}



}