package Base.Players;

import Base.Coordinates;
import Base.Difficulty;
import Base.Direction;
import Base.Gurkins.*;
import Base.Players.Player;

import java.util.Random;

public class AI extends Player {
    private double[][] attackWeights;

    private Difficulty difficulty;

    public AI() {
        this.name = ("AI Player");
        attackWeights = new double[10][10];
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
                setAttackWeights();
                return generateMediumAttack();
            case Hard:
                return generateHardAttack();
            default:
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
        double maxWeight = 0;
        Random rand = new Random();
        int rX = rand.nextInt(10);
        int rY = rand.nextInt(10);
        Coordinates attack = new Coordinates(rX, rY);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (attackWeights[i][j] > maxWeight && getShotResults()[i][j].equals(' ')) {
                    maxWeight = attackWeights[i][j];
                    attack = new Coordinates(i, j);
                }
            }
        }
        return attack;
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




}
