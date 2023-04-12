package Base;

import java.util.Scanner;

public class ConsoleUI {
    Player player1;
    Player player2;

    public ConsoleUI() {
        boolean game_over = false;

        Scanner sc = new Scanner(System.in);
        // Initialisation of players
        System.out.print("Name of player 1: ");
        String player1Name = sc.next();
        System.out.print("Name of player 2: ");
        String player2Name = sc.next();

        Player player1 = new Player();
        Player player2 = new Player();

        Turn c = new Turn();
        while (!game_over) {
            c.changeTurn();
            if (c.turn.equals("1")) {
                System.out.println(player1.name + " please attack " +player2.name + ": ");
                player1.attack_round(player2.getGurkinBoard());
                game_over = player1.checkWin();
            } else if (c.turn.equals("2")) {
                System.out.println(player2.name + " please attack " +player1.name + ": ");
                player2.attack_round(player1.getGurkinBoard());
                game_over = player2.checkWin();
            }
        }
    }

}
