package Base;

public class Gurkin {
    lives int;
    String GurkinID = "None";
    Coordinates[] coordinates;

    public void decrementLives() {
        this.lives = this.lives - 1
    }

}
class Zuchinni extends Gurkin {
    int lives = 3;
    public Zuchinni() {
        this.GurkinID = "Supremo";
    }
}
class Yardlong extends Gurkin {
    int lives = 5;
    public Yardlong() {
        this.GurkinID = "Yardlong";
    }

}






