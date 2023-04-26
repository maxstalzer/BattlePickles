package Base;

public class Turn { // Turn class

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
