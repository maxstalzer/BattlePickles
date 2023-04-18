package Base.Players.mcts;

import Base.Coordinates;

import java.util.ArrayList;

public class Node {
    private final GameState state;
    private final Node parent;
    private final ArrayList<Node> children;
    private int visits;
    private double score;

    public Node(GameState state, Node parent) {
        this.state = state;
        this.parent = parent;
        this.children = new ArrayList<>();
        this.visits = 0;
        this.score = 0;
    }

    public GameState getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public int getVisits() {
        return visits;
    }

    public double getScore() {
        return score;
    }

    public void addChildren(Node child) {
        children.add(child);
    }

    public void updateScore(double score) {
        this.visits++;
        this.score += score;
    }

    public double getAverageScore() {
        if (visits == 0) {
            return 0;
        }
        return score / visits;
    }

    public ArrayList<Coordinates> getPossibleActions(GameState state) {
        ArrayList<Coordinates> possibleActions = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Coordinates coords = new Coordinates(i, j);
                if (!state.isAttacked(coords)) {
                    possibleActions.add(coords);
                }
            }
        }
        return possibleActions;
    }

    public boolean isFullyExpanded() {
        return children.size() == getPossibleActions(state).size();
    }

}
