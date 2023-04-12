package Base;

public class Turn {

    private String turn;
    public void changeTurn() {
        if (turn == null)
            turn = "1";
        else if (turn == "1") {
            turn ="2";
        } else {
            turn = "1";
        }
    }
    public String getTurn() {
        return turn;
    }

}
