package Base;

import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        boolean game_over = false;

        Scanner sc = new Scanner(System.in);
        // Initialisation of players

        Player player1 = new Player();
        Player player2 = new Player();

        Turn c = new Turn();
        /*
        placement of the ships

        */
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
