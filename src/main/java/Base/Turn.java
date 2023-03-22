package Base;

public class Turn {

    String turn;
    public String changeTurn(String n) {
        if (n == null)
            return  turn = "1";
        else if (n == "1") {
            return turn ="2";
        } else {
            return turn = "1";
        }
    }
}
