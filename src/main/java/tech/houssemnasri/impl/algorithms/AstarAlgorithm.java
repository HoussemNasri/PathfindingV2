package tech.houssemnasri.impl.algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import tech.houssemnasri.CostEntity;
import tech.houssemnasri.api.algorithms.BaseAlgorithm;
import tech.houssemnasri.api.algorithms.cost.IAstarCost;
import tech.houssemnasri.api.grid.IGrid;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.node.IPosition;
import tech.houssemnasri.impl.command.CloseNodeCommand;
import tech.houssemnasri.impl.command.OpenNodeCommand;
import tech.houssemnasri.impl.node.PPosition;

/** A* implementation */
public class AstarAlgorithm extends BaseAlgorithm {
    private final Set<INode> openNodes = new HashSet<>();
    private final Set<INode> closedNodes = new HashSet<>();
    private INode currentNode;

    public AstarAlgorithm(IGrid grid, boolean isDiagonalAllowed) {
        super(grid, isDiagonalAllowed);
        initAstarNodes();
    }

    public AstarAlgorithm(IGrid grid) {
        this(grid, false);
    }

    private void initAstarNodes() {
        for (int x = 0; x < grid.getColumns(); x++) {
            for (int y = 0; y < grid.getRows(); y++) {
                IPosition thisNodePosition = PPosition.of(x, y);
                IPosition destNodePosition = grid.getDestinationPosition();
                INode thisNode = grid.getNode(thisNodePosition);
                thisNode.setCostEntity(new CostEntity(new int[] {0, 0, 0}));
                IAstarCost astarCost = new AstarCostAdapter(thisNode.getCostEntity());
                astarCost.updateHCost(manhattanDistance(thisNodePosition, destNodePosition));
            }
        }
    }

    private int manhattanDistance(IPosition a, IPosition b) {
        int dx = Math.abs(a.getX() - b.getX());
        int dy = Math.abs(a.getY() - b.getY());

        return 10 * (dx + dy);
    }

    @Override
    public void forward() {
        if (openNodes.isEmpty()) {
            new OpenNodeCommand(this, grid.getNode(grid.getSourcePosition())).execute();
        }
        setCurrentNode(getLeastCostNode());
        new CloseNodeCommand(this, getCurrentNode()).execute();

        if (isPathFound()) {
            doTraceBackPath();
            return;
        }

        List<INode> neighbors = getCurrentNodeNeighbors();
        IAstarCost currentNodeCost = new AstarCostAdapter(getCurrentNode().getCostEntity());
        for (INode nei : neighbors) {
            if (isNodeClosed(nei)) {
                continue;
            }
            IAstarCost neighborNodeCost = new AstarCostAdapter(nei.getCostEntity());
            int gCostForCurrent = currentNodeCost.gCost();
            int gCostForNeighbor = neighborNodeCost.gCost();
            int gCostForNeighborUpdate = gCostForCurrent + 10;
            if (isNodeOpen(nei)) {
                if (gCostForNeighbor > gCostForNeighborUpdate) {
                    neighborNodeCost.updateGCost(gCostForNeighborUpdate);
                    nei.setParent(currentNode);
                }
            } else {
                neighborNodeCost.updateGCost(gCostForNeighborUpdate);
                nei.setParent(currentNode);
                new OpenNodeCommand(this, nei).execute();
            }
        }
    }

    private boolean isNodeClosed(INode node) {
        return getClosedSet().contains(node);
    }

    private boolean isNodeOpen(INode node) {
        return getOpenSet().contains(node);
    }

    private void doTraceBackPath() {
        INode tempNode = currentNode;
        while (tempNode != null){
            tempNode.setType(INode.Type.PATH);
            tempNode = tempNode.getParent();
        }
    }

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

    private INode getLeastCostNode() {
        PriorityQueue<INode> priorityQueue = new PriorityQueue<>(new AstarNodeComparator());
        priorityQueue.addAll(openNodes);
        return priorityQueue.poll();
    }

    @Override
    public void back() {}

    private void setCurrentNode(INode currentNode) {
        this.currentNode = currentNode;
    }

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
