package tech.houssemnasri.api.algorithms;

import tech.houssemnasri.api.grid.IGrid;

public abstract class BasePathfindingAlgorithm {
    protected final IGrid grid;

    public BasePathfindingAlgorithm(IGrid grid) {
        this.grid = grid;
    }

    public IGrid getGrid() {
        return grid;
    }
}
