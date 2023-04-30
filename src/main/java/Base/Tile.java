package Base;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Tile")
public class Tile {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private boolean gurkin; // If there is a gurkin on the tile
    @DatabaseField(canBeNull = false)
    private boolean isHit = false; // If the tile has been hit

    //added a bunch of variables and methods for online to work besides the @database ads -V
    @DatabaseField(canBeNull = false)
    private int x;
    @DatabaseField(canBeNull = false)
    private int y;
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private Board board;
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private Conichon conichon;
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private Gherkin gherkin;
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private Pickle pickle;
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private Yardlong yardlong;
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private Zuchinni zuchinni;

    private String gurkinChar;
    private Gurkin gurkinID; // The gurkin on the tile

    public void setConichon(Conichon conichon) {
        this.conichon = conichon;
        this.gurkinID = conichon;
    }
    public void setGherkin(Gherkin gherkin) {
        this.gherkin = gherkin;
        this.gurkinID = gherkin;
    }
    public void setPickle(Pickle pickle) {
        this.pickle = pickle;
        this.gurkinID = pickle;
    }
    public void setYardlong(Yardlong yardlong) {
        this.yardlong = yardlong;
        this.gurkinID = yardlong;
    }
    public void setZuchinni(Zuchinni zuchinni) {
        this.zuchinni = zuchinni;
        this.gurkinID = zuchinni;
    }
    public Tile() {}

    public int getX(){
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getId() {
        return id;
    }
    public void setBoard(Board b) {
        this.board = b;
    }

    public Board getBoard() {
        return this.board;
    }
    public int getBoardId() {
        return board.getId();
    }

    public boolean hasGurkin() {
        return this.gurkin;
    } // Getters
    public void setGurkin(Gurkin x) { // Set gurkin on tile
        if (x instanceof Conichon) {
            this.gurkin = true;
            this.conichon = (Conichon) x;
            this.gurkinID = x;
        }
        if (x instanceof Gherkin) {
            this.gurkin = true;
            this.gherkin = (Gherkin) x;
            this.gurkinID = x;
        }
        if (x instanceof Pickle) {
            this.gurkin = true;
            this.pickle = (Pickle) x;
            this.gurkinID = x;
        }
        if (x instanceof Yardlong) {
            this.gurkin = true;
            this.yardlong = (Yardlong) x;
            this.gurkinID = x;
        }
        if (x instanceof Zuchinni) {
            this.gurkin = true;
            this.zuchinni = (Zuchinni) x;
            this.gurkinID = x;
        } if (x instanceof Terrain) {
            this.gurkin = true;
            this.gurkinID = x;
        }
    }
    public void hitTile() {this.isHit = true;} // Hit the tile
    public Gurkin getGurkin() {
        if (!(conichon == null)) {
            return conichon;
        } else if (!(gherkin == null)) {
            return gherkin;
        } else if (!(pickle == null)) {
            return pickle;
        } else if (!(yardlong == null)) {
            return yardlong;
        } else if (!(zuchinni == null)) {
            return zuchinni;
        } else {
            return gurkinID;
        }
    } // Get the gurkin on the tile
    public boolean isHit() {
        return this.isHit;
    } // Check if the tile has been hit
//    public void setChar(Gurkin gurkin) {this.gurkinChar = Character.toString(gurkinID.toChar());}
//    public Character toChar() { // Convert tile to char
//        if (hasGurkin()) {
//            return gurkinID.toChar();
//        }
//        return ' ';
//    }

    public Boolean check(Gurkin gurk) {
        if (!hasGurkin()) return false;
        return gurkinID.equals(gurk);
    }

    public Conichon getConichon() {return conichon;}
    public Gherkin getGherkin() {return gherkin;}
    public Pickle getPickle() {return pickle;}
    public Yardlong getYardlong() {return yardlong;}
    public Zuchinni getZuchinni() {return zuchinni;}

    public void setId(int id) {this.id = id;
    }
    public void setisGurkin(boolean b) {this.gurkin = b;}
    public Boolean getisGurkin() {return this.gurkin;}

    public void setisHit(boolean hit) {this.isHit = hit;
    }
}
