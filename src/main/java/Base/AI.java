package Base;

import Base.Gurkins.*;

import java.util.Random;

public class AI extends Player{

    private Difficulty difficulty;

    public AI() {
        this.name = "AI Player";
        generateBoard();
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void generateBoard() {
        Gurkin[] gurkArr = {new Yardlong(), new Conichon(),  new Pickle(), new Gherkin(), new Zuchinni()};

        for (Gurkin gurk : gurkArr) {
            Boolean valid = false;
            Random rand = new Random();
            Direction.direction dir;
            while (!valid) {
                int randDir = rand.nextInt(2);
                if (randDir == 1) {
                    dir = Direction.direction.Vertical;
                } else {
                    dir = Direction.direction.Horizontal;
                }
                int rX = rand.nextInt(10);
                int rY = rand.nextInt(10);
                valid = validGurkinSetup(gurk, dir, new Coordinates(rX, rY));
            }
        }
    }

    public Coordinates generateAttack() {
        switch (difficulty) {
            case Easy:
                return generateEasyAttack();
            case Medium:
                return generateMediumAttack();
            case Hard:
                return generateHardAttack();
            default:
                return generateEasyAttack();
        }
    }
    public Coordinates generateEasyAttack() {
        Random rand = new Random();
        int rX = rand.nextInt(10);
        int rY = rand.nextInt(10);
        return new Coordinates(rX, rY);
    }

    public Coordinates generateMediumAttack() {

    }
    
}
