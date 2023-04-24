package Base;

import java.util.Objects;

public class Turn {

    private static String turn; // Turn

    public static void init_turn() { // Initialize turn
        turn = "1";
    }
    public static void changeTurn() { // Change turn
        if (turn.equals("1")) {
            turn = "2";
        } else {
            turn = "1";
        }
    }
    public static String getTurn() {
        return turn;
    } // Getters

}
