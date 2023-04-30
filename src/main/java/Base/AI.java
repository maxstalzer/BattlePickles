package Base;

import java.util.ArrayList;
import java.util.Random;

public class AI extends Player {
    private double[][] attackWeights; // Used to determine the best attack for the AI to make

    private Difficulty difficulty; // The difficulty of the AI

    private ArrayList<Coordinates> knownGurkinLocations; // Used to store the locations of gurkins that the AI has found for Hard difficulty

    private Player opponent; // The opponent of the AI

    public void setDifficulty(String difficulty) {
        if (difficulty.equals("Easy")) {
            this.difficulty = Difficulty.Easy;
        } else if (difficulty.equals("Medium")) {
            this.difficulty = Difficulty.Medium;
        } else {
            this.difficulty = Difficulty.Hard;
        }
    }

    public AI(int id) {}

    public AI() { // Constructor
        setName("AI Player");
        attackWeights = new double[10][10];
        generateBoard();
    }

    public void setDifficulty(Difficulty difficulty, Player opponent) { // Sets the difficulty of the AI
        this.opponent = opponent;
        this.difficulty = difficulty;
        if (difficulty == Difficulty.Easy) {
            setDifficultyString("Easy");
        } else if (difficulty == Difficulty.Medium) {
            setDifficultyString("Medium");
        } else {setDifficultyString("Hard");}

    }

    public Difficulty getDifficulty() { // Returns the difficulty of the AI
        return difficulty;
    }

    public void generateBoard() {// Generates a random board for the AI
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

    public Coordinates generateAttack() { // Generates an attack based on the difficulty of the AI
        switch (difficulty) {
            case Medium -> {
                setAttackWeights();
                return generateMediumAttack();
            }
            case Hard -> {
                return generateHardAttack();
            }
            default -> {
                return generateEasyAttack();
            }
        }
    }
    // Generate a random attack that is not a repeat of a preivous attack
    private Coordinates generateEasyAttack() {
        Random rand = new Random();
        int rX = rand.nextInt(10);
        int rY = rand.nextInt(10);
        Coordinates attack = new Coordinates(rX, rY);
        while (getShotResults()[rX][rY] != null) {
            rX = rand.nextInt(10);
            rY = rand.nextInt(10);
            attack = new Coordinates(rX, rY);
        }
        return attack;
    }
    // Generates an attack based on the weights of the attackWeights array
    private Coordinates generateMediumAttack() {
        // Create a list of candidate attack coordinates
        ArrayList<Coordinates> candidates = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (getShotResults()[i][j] == null) {
                    candidates.add(new Coordinates(i, j));
                }
            }
        }

        // Sort the candidates by weight in descending order
        candidates.sort((c1, c2) -> Double.compare(attackWeights[c2.getX()][c2.getY()], attackWeights[c1.getX()][c1.getY()]));
        // filter out the ones that have already been hit
        candidates.removeIf(n -> getShotResults()[n.getX()][n.getY()] != null);

        // finding the maximum weight in the attackweights matrix
        int maxX = candidates.get(0).getX();
        int maxY = candidates.get(0).getY();
        double maxWeight = attackWeights[maxX][maxY];

        // removing all values below the max weight
        ArrayList<Coordinates> filteredArray = new ArrayList<>();
        int x;
        int y;

        for (int i = 0; i < candidates.size(); i++) {
            x = candidates.get(i).getX();
            y = candidates.get(i).getY();
            if (attackWeights[x][y] == maxWeight) {
                filteredArray.add(candidates.get(i));
            }
        }

        // randomly attack an element of that array
        Random rand = new Random();
        // Get a random element from filteredArray
        Coordinates targetCoord = filteredArray.get(rand.nextInt(filteredArray.size()));
        return targetCoord;
    }


    // Increase the weight of a coordinate if it is adjacent to a hit
    // decrease the weight of a coordinate if it is adjacent to a kill
    // normalise the weights to between 0 and 1
    private void setAttackWeights() {
        double maxWeight = 0;
        double minWeight = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (getShotResults()[i][j] != null) {
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
                    }
                     else if (getShotResults()[i][j].equals('o')) {
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
        double maxVal = -1000;
        double minVal = 1000;


        for (int y = 0; y < 10 ; y++) {
            for (int x = 0; x < 10; x++) {
                if (attackWeights[x][y] > maxVal) {
                    maxVal = attackWeights[x][y];
                } if (attackWeights[x][y] < minVal) {
                    minVal = attackWeights[x][y];
                }
            }
        }

        // normalise the weights to between 0 and 1
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (maxVal == minVal) {
                    attackWeights[i][j] = 0.5;
                } else {
                    attackWeights[i][j] = (attackWeights[i][j] - minVal) / (maxVal - minVal);
                }
            }
        }
    }

    private Coordinates generateHardAttack() {
        if (knownGurkinLocations == null) { // if it hasnt been initialised, initialise it
                knownGurkinLocations = new ArrayList<>();
                for (int y = 0; y < 10; y++) {
                    for (int x = 0; x < 10; x++) {
                        if (opponent.getGurkinBoard().getTile(new Coordinates(x, y)).hasGurkin() && !opponent.getGurkinBoard().getTile(new Coordinates(x,y)).isHit()) {
                            knownGurkinLocations.add(new Coordinates(x, y));
                        }
                    }
                }
            }
        return knownGurkinLocations.remove(0);

    }

    public enum Difficulty { // Enum for the difficulty of the AI
        Easy,
        Medium,
        Hard

    }


}
