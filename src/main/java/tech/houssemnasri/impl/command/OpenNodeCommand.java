package tech.houssemnasri.impl.command;

import tech.houssemnasri.api.algorithms.BaseAlgorithm;
import tech.houssemnasri.api.command.AlgoCommand;
import tech.houssemnasri.api.node.INode;

public class OpenNodeCommand extends AlgoCommand {

    public OpenNodeCommand(BaseAlgorithm algorithm, INode commandNode) {
        super(algorithm, commandNode);
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
