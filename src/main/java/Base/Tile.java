package Base;

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
    public void hitTile() {
        this.isHit = true;
    }
    public Gurkin getGurkin() {
        return gurkinID;
    }
    public boolean isHit() {
        return this.isHit;
    }
}
