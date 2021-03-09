package tech.houssemnasri.impl.command;

import tech.houssemnasri.api.algorithms.BaseAlgorithm;
import tech.houssemnasri.api.command.AlgoCommand;

public class CloseNodeCommand extends AlgoCommand {
    public CloseNodeCommand(BaseAlgorithm algorithm) {
        super(algorithm);
    }

    @Override
    public void execute() {}

    @Override
    public void undo() {}
}
