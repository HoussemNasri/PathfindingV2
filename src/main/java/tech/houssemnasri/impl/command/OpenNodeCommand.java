package tech.houssemnasri.impl.command;

import tech.houssemnasri.api.pathfinder.BaseAlgorithm;
import tech.houssemnasri.api.command.AlgorithmCommand;
import tech.houssemnasri.api.node.INode;
import static tech.houssemnasri.api.node.INode.*;

public class OpenNodeCommand extends AlgorithmCommand {

    public OpenNodeCommand(BaseAlgorithm algorithm, INode node) {
        super(algorithm, node);
    }

    @Override
    public void execute() {
        if (isNodeOpen()) {
            return;
        }
        algorithm.getOpenSet().add(node);
        node.setType(Type.OPEN);
    }

    private boolean isNodeOpen() {
        return algorithm.getOpenSet().contains(node);
    }

    @Override
    public void undo() {
        algorithm.getOpenSet().remove(node);
        node.setType(Type.BASIC);
    }
}
