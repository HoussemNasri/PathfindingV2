package tech.houssemnasri.impl.algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tech.houssemnasri.api.algorithms.BaseAlgorithm;
import tech.houssemnasri.api.grid.IGrid;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.node.IPosition;
import tech.houssemnasri.impl.node.PPosition;

public class AstarAlgorithm extends BaseAlgorithm {
    private final Set<INode> openNodes = new HashSet<>();
    private final Set<INode> closedNodes = new HashSet<>();
    private INode currentNode;

    public AstarAlgorithm(IGrid grid, boolean isDiagonalAllowed) {
        super(grid, isDiagonalAllowed);
    }

    public AstarAlgorithm(IGrid grid) {
        super(grid);
    }

    @Override
    public void forward() {}

    private List<INode> getCurrentNodeNeighbors() {
        List<INode> result = new ArrayList<>();
        int currX = currentNode.getPosition().getX();
        int currY = currentNode.getPosition().getY();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int neiX = currX + i;
                int neiY = currY + j;
                IPosition neiPosition = PPosition.of(neiX, neiY);

                if (not(isPositionValid(neiPosition))) {
                    continue;
                } else if (not(isWalkable(neiPosition))) {
                    continue;
                } else if (PPosition.of(currX, currY).equals(neiPosition)) {
                    continue;
                } else if (not(isDiagonalAllowed())) {
                    if (Math.abs(i) - Math.abs(j) == 0) {
                        continue;
                    }
                }
                result.add(grid.getNode(neiPosition));
            }
        }
        return result;
    }

    @Override
    public void back() {}

    @Override
    public INode getCurrentNode() {
        return currentNode;
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
