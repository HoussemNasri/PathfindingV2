package tech.houssemnasri.impl.command;

import tech.houssemnasri.api.algorithms.BaseAlgorithm;
import tech.houssemnasri.api.command.AlgoCommand;
import tech.houssemnasri.api.node.INode;
import static tech.houssemnasri.api.node.INode.*;

public class CloseNodeCommand extends AlgoCommand {
    public CloseNodeCommand(BaseAlgorithm algorithm, INode commandNode) {
        super(algorithm, commandNode);
    }

    @Override
    public void execute() {
        if (isNodeClosed()) {
            return;
        }
        algorithm.getOpenSet().remove(commandNode);
        algorithm.getClosedSet().add(commandNode);
        commandNode.setType(Type.CLOSED);
    }

    private boolean isNodeClosed() {
        return algorithm.getClosedSet().contains(commandNode);
    }

    @Override
    public void undo() {
        algorithm.getOpenSet().add(commandNode);
        algorithm.getClosedSet().remove(commandNode);
        commandNode.setType(Type.OPEN);
    }
}
