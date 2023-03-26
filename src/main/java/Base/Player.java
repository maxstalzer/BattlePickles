package Base;

import java.util.Scanner;

public class Player {
    String name;
    int remaining_gurkins = 5;

    Board gurkinBoard;

    Character[][] shotResults = new Character[10][10];

    public Board getGurkinBoard() {
        return gurkinBoard;
    }

    public Player(String name) {
        this.name = name;
        this.gurkinBoard = new Board();
    }

    private void displayShotBoard() {
        System.out.println("+---------------------+");
        System.out.println("  0 1 2 3 4 5 6 7 8 9 ");
        for (int i = 0; i < 10; i++) {
            System.out.print("| ");
            for (int j = 0; j < 10; j++) {
                if (shotResults[j][i] == null) {
                    System.out.print("  ");
                } else {
                    System.out.print( shotResults[j][i] + " ");
                }
            }
            System.out.println(" |");
        }
        System.out.println("  0 1 2 3 4 5 6 7 8 9 ");
        System.out.println("+---------------------+");

    }

    public void attack_round(Board board) {
        displayShotBoard();
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        int y = sc.nextInt();

        Coordinates coords = new Coordinates(x, y);
        boolean validEntry = coords.validCoords();
        while (!validEntry) {
            System.out.println("Please enter new x and y coordinates");
            int xPrime = sc.nextInt();
            int yPrime = sc.nextInt();
            coords.updateCoords(xPrime, yPrime);
            validEntry = coords.validCoords();
        }

        shoot(board, coords);

    }

//  Allows a player to shoot at given coordinates on the opposing player's board
    private void shoot(Board board, Coordinates coords) {
        String result = board.attack(coords);
        int x = coords.getX();
        int y = coords.getY();

//      if the player successfully hits an opponents pickle the player's shots
//      board is updated with x for hit, o for miss and the whole pickle is
//      updated with k
        if (result.equals("hit")) {
            this.shotResults[x][y] = 'x';
            System.out.println("Hit!");
            Gurkin gurk = board.getTile(coords).getGurkin();
            if (gurk.deadGurk()) {
                for (int i = 0; i < gurk.getSize(); i++) {
                   int xCor = gurk.getGurkinCoors()[i].getX();
                   int yCor = gurk.getGurkinCoors()[i].getY();
                   this.shotResults[xCor][yCor] = 'k';

                }
                System.out.println("You killed a gurkin");
            }
        } else if (result.equals("miss")) {
            this.shotResults[x][y] = 'o';
            System.out.println("Miss lol");
        } else if (result.equals("noob")) {
            System.out.println("Already hit here");
            Boolean validEntry = coords.validCoords();

            while (!validEntry) {
                System.out.println("Please enter new x and y coordinates");
                Scanner sc = new Scanner(System.in);
                int xPrime = sc.nextInt();
                int yPrime = sc.nextInt();
                coords.updateCoords(xPrime, yPrime);
                validEntry = coords.validCoords();
            }
            shoot(board, coords);
        }
    }
}

