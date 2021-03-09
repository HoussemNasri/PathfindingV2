package tech.houssemnasri.impl.command;

import tech.houssemnasri.api.algorithms.BaseAlgorithm;
import tech.houssemnasri.api.command.AlgoCommand;
import tech.houssemnasri.api.node.INode;
import static tech.houssemnasri.api.node.INode.*;

public class CloseNodeCommand extends AlgoCommand {
    public CloseNodeCommand(BaseAlgorithm algorithm, INode node) {
        super(algorithm, node);
    }

    @Override
    public void execute() {
        if (isNodeClosed()) {
            return;
        }
        algorithm.getOpenSet().remove(node);
        algorithm.getClosedSet().add(node);
        node.setType(Type.CLOSED);
    }

    private boolean isNodeClosed() {
        return algorithm.getClosedSet().contains(node);
    }

    @Override
    public void undo() {
        algorithm.getOpenSet().add(node);
        algorithm.getClosedSet().remove(node);
        node.setType(Type.OPEN);
    }
}
