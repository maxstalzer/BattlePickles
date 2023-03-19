package Base;

public class Gurkin {
    int lives;
    String GurkinID = "None";
    Coordinates[] coordinates;

    public void decrementLives() {
        this.lives --;
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






