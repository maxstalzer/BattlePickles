package Base;

public abstract class Gurkin {
    private int size;
    private int lives;
    private String GurkinID;
    public Coordinates[] coordinates;

//    public Placement.Direction direction;
    public void setCoordinates(Coordinates start, Placement.Direction direction) {
        if (direction.equals(Placement.Direction.Horizontal)){
            for (int i = 0; i < size ; i++) {
                coordinates[i] = new Coordinates(start.getX() + i, start.getY());
            }
        } else {
            for (int i = 0; i < size ; i++) {
                coordinates[i] = new Coordinates(start.getX(), start.getY() + i);
            }
        }
    }
//  Constructor for the class
    Gurkin(int size, String id, Coordinates start, Placement.Direction direction) {
        this.coordinates = new Coordinates[size];
        this.GurkinID = id;
        this.size = size;
        this.lives = size;
       setCoordinates(start, direction);

    }
    public int getSize() {
        return this.size;
    }
    public void decrementLives() {
        this.lives --;
    }
    
    public Coordinates[] getGurkinCoors() {
        return this.coordinates;
    }

    public boolean deadGurk() {
        return this.lives < 1;
    }

//  Method to return the string representation of each gurkin
     abstract Character toChar();

}
class Zuchinni extends Gurkin {
    public Zuchinni(Coordinates start, Placement.Direction direction) {
        super(4, "Zuchinni", start, direction);
    }

    public Character toChar() {
        return 'z';
    }
}

class Yardlong extends Gurkin {
    public Yardlong(Coordinates start, Placement.Direction direction) {
        super(5, "Yardlong", start, direction);
    }
    public Character toChar() {
        return 'y';
    }
}

class Gherkin extends Gurkin {

    public Gherkin(Coordinates start, Placement.Direction direction) {
        super(3, "Gherkin", start, direction);
    }
    public Character toChar() {
        return 'g';
    }

}

class Pickle extends Gurkin {

    public Pickle(Coordinates start, Placement.Direction direction) {
        super(3, "Pickle", start, direction);
    }

    public Character toChar() {
        return 'p';
    }
}

class Conichon extends  Gurkin {
    public Conichon(Coordinates start, Placement.Direction direction) {
        super(2, "Conichon", start, direction);
    }
    public Character toChar() {
        return 'c';
    }
}

class Terrain extends Gurkin {
    public Terrain(Coordinates start, Placement.Direction direction) {
        super(2, "Terrain", start, direction);
    }
    public Character toChar() {
        return 'T';
    }
}

