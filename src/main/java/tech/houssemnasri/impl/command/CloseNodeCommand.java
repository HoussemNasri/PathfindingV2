package tech.houssemnasri.impl.command;

import tech.houssemnasri.api.algorithms.BaseAlgorithm;
import tech.houssemnasri.api.command.AlgoCommand;
import tech.houssemnasri.api.node.INode;

public class CloseNodeCommand extends AlgoCommand {

    public CloseNodeCommand(BaseAlgorithm algorithm, INode commandNode) {
        super(algorithm, commandNode);
    }

    @Override
    public void execute() {}

    @Override
    public void undo() {}
}
