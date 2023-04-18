package Base.Players.mcts;
import Base.Board;
import Base.Coordinates;
import Base.Game;


public class GameState {
    private final Character[][] shotResults;
    private final Board opponentBoard;

    public GameState(Character[][] shotResults, Board opponentBoard) {
        this.shotResults = shotResults;
        this.opponentBoard = opponentBoard;
    }

    public Character[][] getShotResults() {
        return shotResults;
    }

    public boolean isAttacked(Coordinates coords) {
        return shotResults[coords.getX()][coords.getY()] != null;
    }

    public boolean isDead(Coordinates coords) {
        if(opponentBoard.getTile(coords).getGurkin() == null) return false;
        return opponentBoard.getTile(coords).getGurkin().deadGurk();
    }


}
