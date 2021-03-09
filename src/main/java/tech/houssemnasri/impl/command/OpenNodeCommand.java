package tech.houssemnasri.impl.command;

import tech.houssemnasri.api.algorithms.BaseAlgorithm;
import tech.houssemnasri.api.command.AlgoCommand;

public class OpenNodeCommand extends AlgoCommand {

    public OpenNodeCommand(BaseAlgorithm algorithm) {
        super(algorithm);
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
