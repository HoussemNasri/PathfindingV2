package tech.houssemnasri.impl.algorithms;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import tech.houssemnasri.CostEntity;
import tech.houssemnasri.api.algorithms.BaseAlgorithm;
import tech.houssemnasri.api.algorithms.Distance;
import tech.houssemnasri.api.algorithms.cost.IAstarCost;
import tech.houssemnasri.api.grid.IGrid;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.node.IPosition;
import tech.houssemnasri.impl.algorithms.distance.ManhattanDistance;
import tech.houssemnasri.impl.command.CloseNodeCommand;
import tech.houssemnasri.impl.command.OpenNodeCommand;

/** A* implementation */
public class AstarAlgorithm extends BaseAlgorithm {
    private static final int HORIZ_VERT_DISTANCE = 10;
    private static final int DIAGONAL_DISTANCE = 14;

    private final Set<INode> openNodes = new HashSet<>();
    private final Set<INode> closedNodes = new HashSet<>();
    private INode currentNode;

    public AstarAlgorithm(IGrid grid, boolean isDiagonalAllowed) {
        super(grid, isDiagonalAllowed);
    }

    public AstarAlgorithm(IGrid grid) {
        super(grid, false);
    }

    @Override
    protected void initialize() {
        grid.stream().forEach(node -> {
            IPosition thisPosition = node.getPosition();
            IPosition destPosition = node.getPosition();
            CostEntity thisCost = new CostEntity(new int[3]);
            node.setCostEntity(thisCost);
            Distance distance = new ManhattanDistance(HORIZ_VERT_DISTANCE);
            new AstarCostAdapter(thisCost)
                    .updateHCost(distance.apply(thisPosition, destPosition));

        });
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
            int gCostForNeighborUpdate = gCostForCurrent + HORIZ_VERT_DISTANCE;
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
