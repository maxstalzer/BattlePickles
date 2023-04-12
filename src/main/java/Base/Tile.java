package Base;

import Base.Gurkins.Gurkin;

public class Tile {
    private boolean gurkin;
    private Gurkin gurkinID;
    private boolean isHit = false;

    public boolean hasGurkin() {
        return this.gurkin;
    }
    public void setGurkin(Gurkin x) {
        this.gurkin = true;
        this.gurkinID = x;
    }
    public void hitTile() {this.isHit = true;}
    public Gurkin getGurkin() {
        return gurkinID;
    }
    public boolean isHit() {
        return this.isHit;
    }

    public Character toChar() {
        if (hasGurkin()) {
            return gurkinID.toChar();
        }
        return ' ';
    }

    public Boolean check(Gurkin gurk) {
        if (!hasGurkin()) return false;
        return gurkinID.equals(gurk);
    }
}
