package Base.Players.mcts;

import Base.Coordinates;
import Base.Players.AI;
import Base.Players.Player;

public class MCTS {
    private Node root;
    private int iterations;

    public MCTS(Player player, AI agent, int iterations) {
        this.root = new Node(new GameState(agent.getShotResults(), player.getGurkinBoard()), null);
        this.iterations = iterations;
    }

    public Node selectNode (Node node) {
        while (!node.isTerminal()) {
            if (node.isFullyExpanded()) {
                return expand(node);
            }
            node = node.getBestChild();
        }
        return node;
    }

    private Node expand(Node node) {
        for (Coordinates coords : node.getPossibleActions(node.getState())) {
            if (!node.getState().isAttacked(coords)) {
                GameState newState = node.getState().clone();
                newState.simAttack(coords);

                if (!node.hasChild(newState)) {
                    Node newNode = new Node(newState, node);
                    node.addChildren(newNode);
                    return newNode;
                }
            }
        }
        return null;
    }

}
