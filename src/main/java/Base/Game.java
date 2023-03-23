package Base;

import java.util.Arrays;
import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        boolean game_over = false;
        // Player 1 places ships Placement class
        // Player 2 places ships Placement class
        // Player 1 starts (we can decide to randomize the start) Turn class
        //
        Scanner sc = new Scanner(System.in);
        // Initialisation of players and board
        System.out.println("Please Enter the name of player 1: ");
        String player1Name = sc.next();
        System.out.println("Please Enter the name of player 2: ");
        String player2Name = sc.next();

        Player player1 = new Player(player1Name);
        Player player2 = new Player(player2Name);

        Turn c = new Turn();
        /*
        placement of the ships

        */
        System.out.println(player1.name + " please place one of the following ships: ");
        Placement.gurkPlacer(new String[]{"Gherkin", "Zuchinni","Pickle", "Conichon", "Yardlong" },player1);
        while (!game_over) {

            c.changeTurn(c.turn);
        }

    }

}
