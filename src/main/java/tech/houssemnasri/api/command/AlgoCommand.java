package tech.houssemnasri.api.command;

import tech.houssemnasri.api.algorithms.BaseAlgorithm;
import tech.houssemnasri.api.node.INode;

/**
 * This class {@code AlgoCommand} responsible for generalizing common actions performed by
 * pathfinding algorithms like opening a node or closing it.
 */
public abstract class AlgoCommand implements ICommand {
    protected BaseAlgorithm algorithm;
    /** The node to perform this action on. */
    protected INode node;

    public AlgoCommand(BaseAlgorithm algorithm, INode node) {
        this.algorithm = algorithm;
        this.node = node;
    }

    /** Returns the algorithm object using this command. */
    public BaseAlgorithm getAlgorithm() {
        return algorithm;
    }

    public INode getNode() {
        return node;
    }
}
