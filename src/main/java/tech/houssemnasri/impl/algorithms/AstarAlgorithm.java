package tech.houssemnasri.impl.algorithms;

import java.util.HashSet;
import java.util.Set;

import tech.houssemnasri.api.algorithms.BaseAlgorithm;
import tech.houssemnasri.api.grid.IGrid;
import tech.houssemnasri.api.node.INode;

public class AstarAlgorithm extends BaseAlgorithm {
    private final Set<INode> openNodes = new HashSet<>();
    private final Set<INode> closedNodes = new HashSet<>();

    public AstarAlgorithm(IGrid grid, boolean isDiagonalAllowed) {
        super(grid, isDiagonalAllowed);
    }

    public AstarAlgorithm(IGrid grid) {
        super(grid);
    }

    @Override
    public void forward() {}

    @Override
    public void back() {}

    @Override
    public INode getCurrentNode() {
        return null;
    }

    @Override
    public Set<INode> getOpenSet() {
        return openNodes;
    }

    @Override
    public Set<INode> getClosedSet() {
        return closedNodes;
    }
}
