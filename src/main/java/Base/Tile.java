package Base;

import Base.Gurkins.Gurkin;

public class Tile {
    private boolean gurkin; // If there is a gurkin on the tile
    private Gurkin gurkinID; // The gurkin on the tile
    private boolean isHit = false; // If the tile has been hit

    public boolean hasGurkin() {
        return this.gurkin;
    } // Getters
    public void setGurkin(Gurkin x) { // Set gurkin on tile
        this.gurkin = true;
        this.gurkinID = x;
    }
    public void hitTile() {this.isHit = true;} // Hit the tile
    public Gurkin getGurkin() {
        return gurkinID;
    } // Get the gurkin on the tile
    public boolean isHit() {
        return this.isHit;
    } // Check if the tile has been hit

    public Character toChar() { // Convert tile to char
        if (hasGurkin()) {
            return gurkinID.toChar();
        }
        return ' ';
    }

    public Boolean check(Gurkin gurk) {
        if (!hasGurkin()) return false;
        return gurkinID.equals(gurk);
    }

    public Tile deepClone() {
        Tile clone = new Tile();
        clone.gurkin = this.gurkin;
        clone.gurkinID = this.gurkinID;
        clone.isHit = this.isHit;
        return clone;
    }
}
