package Base.Players;

import Base.Coordinates;
import Base.Direction;
import Base.Gurkins.*;

import java.util.ArrayList;
import java.util.Random;

public class AI extends Player {
    private double[][] attackWeights;

    private Difficulty difficulty;

    private ArrayList<Coordinates> knownGurkinLocations;

    public AI() {
        this.name = ("AI Player");
        attackWeights = new double[10][10];
        generateBoard();
    }

    public void setDifficulty(Difficulty difficulty, Player opponent) {
        if (difficulty.equals(Difficulty.Easy)) {
            Random rand = new Random();
            double probability = rand.nextDouble();
            if (probability < 0.05 ) {
                difficulty = Difficulty.Impossible;
                knownGurkinLocations = new ArrayList<>();
                for (int y = 0; y < 10; y++) {
                    for (int x = 0; x < 10; x++) {
                        if (opponent.getGurkinBoard().getTile(new Coordinates(x, y)).hasGurkin()) {
                            knownGurkinLocations.add(new Coordinates(x, y));
                        }
                    }
                }
            }
        }
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
            case Medium:
                setAttackWeights();
                return generateMediumAttack();
            case Hard:
                return generateMediumAttack();
//                return generateHardAttack();
            case Impossible:
                return generateImpossibleAttack();
            case Easy:
                return generateEasyAttack();
        }
    }
    // Generate a random attack that is not a repeat of a preivous attack
    public Coordinates generateEasyAttack() {
        Random rand = new Random();
        int rX = rand.nextInt(10);
        int rY = rand.nextInt(10);
        Coordinates attack = new Coordinates(rX, rY);
        while (!getShotResults()[rX][rY].equals(' ')) {
            rX = rand.nextInt(10);
            rY = rand.nextInt(10);
            attack = new Coordinates(rX, rY);
        }
        return attack;
    }
    // Generate an attack based on the weights of the attackWeights array
    public Coordinates generateMediumAttack() {
        // Create a list of candidate attack coordinates
        ArrayList<Coordinates> candidates = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (getShotResults()[i][j].equals(' ')) {
                    candidates.add(new Coordinates(i, j));
                }
            }
        }

        // Sort the candidates by weight in descending order
        candidates.sort((c1, c2) -> Double.compare(attackWeights[c2.getX()][c2.getY()], attackWeights[c1.getX()][c1.getY()]));

        // Choose the highest-weighted point that has not yet been attacked
        for (Coordinates candidate : candidates) {
            if (getShotResults()[candidate.getX()][candidate.getY()].equals(' ')) {
                return candidate;
            }
        }

        // If all candidates have been attacked, choose a random point
        Random rand = new Random();
        int rX = rand.nextInt(10);
        int rY = rand.nextInt(10);
        return new Coordinates(rX, rY);
    }


    // Increase the weight of a coordinate if it is adjacent to a hit
    // decrease the weight of a coordinate if it is adjacent to a kill
    // normalise the weights to between 0 and 1
    private void setAttackWeights() {
        double maxWeight = 0;
        double minWeight = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (getShotResults()[i][j].equals('x')) {
                    if (i > 0) {
                        attackWeights[i - 1][j] += 1;
                        if (attackWeights[i - 1][j] > maxWeight) {
                            maxWeight = attackWeights[i - 1][j];
                        }
                        if (attackWeights[i - 1][j] < minWeight) {
                            minWeight = attackWeights[i - 1][j];
                        }
                    }
                    if (i < 9) {
                        attackWeights[i + 1][j] += 1;
                        if (attackWeights[i + 1][j] > maxWeight) {
                            maxWeight = attackWeights[i + 1][j];
                        }
                        if (attackWeights[i + 1][j] < minWeight) {
                            minWeight = attackWeights[i + 1][j];
                        }
                    }
                    if (j > 0) {
                        attackWeights[i][j - 1] += 1;
                        if (attackWeights[i][j - 1] > maxWeight) {
                            maxWeight = attackWeights[i][j - 1];
                        }
                        if (attackWeights[i][j - 1] < minWeight) {
                            minWeight = attackWeights[i][j - 1];
                        }
                    }
                    if (j < 9) {
                        attackWeights[i][j + 1] += 1;
                        if (attackWeights[i][j + 1] > maxWeight) {
                            maxWeight = attackWeights[i][j + 1];
                        }
                        if (attackWeights[i][j + 1] < minWeight) {
                            minWeight = attackWeights[i][j + 1];
                        }
                    }
                } else if (getShotResults()[i][j].equals('o')) {
                    if (i > 0) {
                        attackWeights[i - 1][j] -= 1;
                        if (attackWeights[i - 1][j] > maxWeight) {
                            maxWeight = attackWeights[i - 1][j];
                        }
                        if (attackWeights[i - 1][j] < minWeight) {
                            minWeight = attackWeights[i - 1][j];
                        }
                    }
                } else if (getShotResults()[i][j].equals('k')) {
                    if (i > 0) {
                        attackWeights[i - 1][j] -= 0.5;
                        if (attackWeights[i - 1][j] > maxWeight) {
                            maxWeight = attackWeights[i - 1][j];
                        }
                        if (attackWeights[i - 1][j] < minWeight) {
                            minWeight = attackWeights[i - 1][j];
                        }
                    }
                }
            }
        }
    }

    public enum Difficulty {
        Easy,
        Medium,
        Hard,
        Impossible

    }

    private Coordinates generateImpossibleAttack() {
        return knownGurkinLocations.remove(0);
    }

}
