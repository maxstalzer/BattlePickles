package Base;

public abstract class Gurkin {
    private final int size;
    private int lives;


//  Constructor for the class
    Gurkin(int size) {
        this.size = size;
        this.lives = size;


    }
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