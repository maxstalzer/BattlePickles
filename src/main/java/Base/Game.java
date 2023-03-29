package Base;

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
        System.out.println("Final placement of " + player1.name + "'s ships");
        player1.getGurkinBoard().displayBoard();

//        Placement of player 2s ships
        Placement.gurkPlacer(new String[]{"Gherkin", "Zuchinni","Pickle", "Conichon", "Yardlong"}, player2);
        System.out.println("Final placement of " + player2.name + "'s ships");
        player2.getGurkinBoard().displayBoard();
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
