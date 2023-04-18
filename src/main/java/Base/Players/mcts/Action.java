package Base.Players.mcts;

import Base.Coordinates;

public class Action {
    private Coordinates coords;
    private enum Result {HIT, MISS};
    private Result result;

    public Action(Coordinates coords, Result result) {
        this.coords = coords;
    }

}
