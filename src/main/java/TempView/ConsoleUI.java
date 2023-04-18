package TempView;

import Base.*;
import Base.Players.Player;

import java.util.Scanner;

public class ConsoleUI {
    Scanner sc;
    Game game;

    public ConsoleUI() {
    //   Ask for Single or multiplayer
        sc = new Scanner(System.in);
        int game_mode = sc.nextInt();
        if (game_mode == 1) {
            game = new Game(false);
    //  Ask for player names
            System.out.print("Player 1 please enter name: ");
            game.getPlayer1().setName(sc.next());
            game.getPlayer2().setName("Bot");
        } else {
            game = new Game(true);
    //        Ask for player names
            System.out.print("Player 1 please enter name: ");
            game.getPlayer1().setName(sc.next());
            System.out.print("Player 2 please enter name: ");
            game.getPlayer2().setName(sc.next());
        }
    }
    public void displayShotBoard(Player player) {
        Character[][] shotResults = player.getShotResults();

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
    public Coordinates getAttackInfo(Player player) {
        displayShotBoard(player);
        System.out.println("Please enter an x and y coordinate to attack");
        int x = sc.nextInt();
        int y = sc.nextInt();

        return new Coordinates(x, y);
    }

}
