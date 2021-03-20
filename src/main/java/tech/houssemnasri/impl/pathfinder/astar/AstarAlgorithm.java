package tech.houssemnasri.impl.pathfinder.astar;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import tech.houssemnasri.api.pathfinder.BaseAlgorithm;
import tech.houssemnasri.api.pathfinder.Distance;
import tech.houssemnasri.api.pathfinder.cost.IAstarCost;
import tech.houssemnasri.api.grid.IGrid;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.node.IPosition;
import tech.houssemnasri.impl.pathfinder.distance.ManhattanDistance;
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
        // Initialize.
    }

    @Override
    public void forward() {
        if (openNodes.isEmpty()) {
            new OpenNodeCommand(this, grid.getNode(grid.getSourcePosition())).execute();
            computeHCost(grid.getNode(grid.getSourcePosition()));
            new AstarCostAdapter(getGrid().getNode(getGrid().getSourcePosition()).getCostEntity()).updateGCost(0);
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
            int gCostForNeighborUpdate =
                    gCostForCurrent
                            + (isNodeOnDiagonalOfCurrent(nei)
                                    ? DIAGONAL_DISTANCE
                                    : HORIZ_VERT_DISTANCE);
            if (isNodeOpen(nei)) {
                if (gCostForNeighbor > gCostForNeighborUpdate) {
                    neighborNodeCost.updateGCost(gCostForNeighborUpdate);
                    nei.setParent(currentNode);
                }
            } else {
                neighborNodeCost.updateGCost(gCostForNeighborUpdate);
                nei.setParent(currentNode);
                computeHCost(nei);
                new OpenNodeCommand(this, nei).execute();
            }
        }
    }

    private void computeHCost(INode node) {
        IPosition thisPosition = node.getPosition();
        IPosition destPosition = grid.getDestinationPosition();
        Distance distance = new ManhattanDistance();
        new AstarCostAdapter(node.getCostEntity())
                .updateHCost(distance.apply(thisPosition, destPosition));
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
