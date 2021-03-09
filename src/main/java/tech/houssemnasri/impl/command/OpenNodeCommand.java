package tech.houssemnasri.impl.command;

import tech.houssemnasri.api.algorithms.BaseAlgorithm;
import tech.houssemnasri.api.command.AlgoCommand;
import tech.houssemnasri.api.node.INode;
import static tech.houssemnasri.api.node.INode.*;

public class OpenNodeCommand extends AlgoCommand {

    public OpenNodeCommand(BaseAlgorithm algorithm, INode commandNode) {
        super(algorithm, commandNode);
    }

    @Override
    public void execute() {
        if (isNodeOpen()) {
            return;
        }
        algorithm.getOpenSet().add(commandNode);
        commandNode.setType(Type.OPEN);
    }

    private boolean isNodeOpen() {
        return algorithm.getOpenSet().contains(commandNode);
    }

    @Override
    public void undo() {
        algorithm.getOpenSet().remove(commandNode);
        commandNode.setType(Type.BASIC);
    }
}
