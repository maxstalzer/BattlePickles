package Base;

import java.util.Objects;

public class Turn {

    private static String turn;

    public Turn() {
        turn = "1";
    }
    public static void changeTurn() {
        if (Objects.equals(turn, "1")) {
            turn ="2";
        } else {
            turn = "1";
        }
    }
    public String getTurn() {
        return turn;
    }

}
