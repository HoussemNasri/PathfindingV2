package tech.houssemnasri.api.command;

import tech.houssemnasri.api.algorithms.BaseAlgorithm;

public abstract class AlgoCommand implements ICommand {
    protected BaseAlgorithm algorithm;

    public AlgoCommand(BaseAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public BaseAlgorithm getAlgorithm() {
        return algorithm;
    }
}
