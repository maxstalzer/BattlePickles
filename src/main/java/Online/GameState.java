package Online;

public class GameState {
    private int id;
    private int CurrentPlayer;
    private String GameData;

    public GameState() {
        // Empty Constructor for ORM}
    }

    //actual Constructor
    public GameState(int id, int CurrentPlayer, String GameData) {
        this.id = id;
        this.CurrentPlayer = CurrentPlayer;
        this.GameData = GameData;
    }

    //Getters and Setters for ORM
    public void setCurrentPlayer(int CurrentPlayer) {
        this.CurrentPlayer = CurrentPlayer;
    }

    public void setGameData(String GameData) {
        this.GameData = GameData;
    }
}
