package tech.houssemnasri.impl.pathfinder.astar;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import tech.houssemnasri.api.pathfinder.BaseAlgorithm;
import tech.houssemnasri.api.pathfinder.Distance;
import tech.houssemnasri.api.pathfinder.cost.IAStarCost;
import tech.houssemnasri.api.grid.IGrid;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.node.IPosition;
import tech.houssemnasri.impl.command.CommandRecord;
import tech.houssemnasri.impl.command.SetCurrentNodeCommand;
import tech.houssemnasri.impl.command.SetParentCommand;
import tech.houssemnasri.impl.pathfinder.distance.ManhattanDistance;
import tech.houssemnasri.impl.command.CloseNodeCommand;
import tech.houssemnasri.impl.command.OpenNodeCommand;

/** A* implementation */
public class AStarAlgorithm extends BaseAlgorithm {
  private static final int HORIZ_VERT_DISTANCE = 10;
  private static final int DIAGONAL_DISTANCE = 14;

  private final Set<INode> openNodes = new HashSet<>();
  private final Set<INode> closedNodes = new HashSet<>();

  public AStarAlgorithm(IGrid grid, boolean isDiagonalAllowed) {
    super(grid, isDiagonalAllowed);
  }

  public AStarAlgorithm(IGrid grid) {
    super(grid, false);
  }

  @Override
  protected void initialize() {
    // Initialize.
  }

  @Override
  public void forward() {
    CommandRecord commandRecord = new CommandRecord();
    if (openNodes.isEmpty()) {
      IPosition source = grid.getSourcePosition();
      commandRecord.push(new OpenNodeCommand(this, grid.getNode(source)).executeAndReturn());
      computeHCost(grid.getNode(source), commandRecord);
      new AStarCostAdapter(getGrid().getNode(source).getPathCost(), commandRecord).setG(0);
    }
    // setCurrentNode(getLeastCostNode());
    commandRecord.push(new SetCurrentNodeCommand(this, getLeastCostNode()).executeAndReturn());
    commandRecord.push(new CloseNodeCommand(this, getCurrentNode()).executeAndReturn());

    if (getGrid().isDestinationNode(getCurrentNode())) {
      saveRecord(commandRecord);
      doTraceBackPath();
      setPathFound(true);
      return;
    }

    List<INode> neighbors = getCurrentNodeNeighbors();
    IAStarCost currentNodeCost =
        new AStarCostAdapter(getCurrentNode().getPathCost(), commandRecord);
    for (INode nei : neighbors) {
      if (isNodeClosed(nei)) {
        continue;
      }
      IAStarCost neighborNodeCost = new AStarCostAdapter(nei.getPathCost(), commandRecord);
      int gCostForCurrent = currentNodeCost.gCost();
      int gCostForNeighbor = neighborNodeCost.gCost();
      int gCostForNeighborUpdate =
          gCostForCurrent + (isOnDiagonal(nei) ? DIAGONAL_DISTANCE : HORIZ_VERT_DISTANCE);
      if (isNodeOpen(nei)) {
        if (gCostForNeighbor > gCostForNeighborUpdate) {
          neighborNodeCost.setG(gCostForNeighborUpdate);
          commandRecord.push(new SetParentCommand(this, nei, currentNode).executeAndReturn());
        }
      } else {
        neighborNodeCost.setG(gCostForNeighborUpdate);
        commandRecord.push(new SetParentCommand(this, nei, currentNode).executeAndReturn());
        computeHCost(nei, commandRecord);
        commandRecord.push(new OpenNodeCommand(this, nei).executeAndReturn());
      }
    }
    saveRecord(commandRecord);
  }

  private void computeHCost(INode node, CommandRecord commandRecord) {
    IPosition thisPosition = node.getPosition();
    IPosition destPosition = grid.getDestinationPosition();
    Distance distance = new ManhattanDistance();
    new AStarCostAdapter(node.getPathCost(), commandRecord)
        .setH(distance.apply(thisPosition, destPosition));
  }

  private INode getLeastCostNode() {
    PriorityQueue<INode> priorityQueue = new PriorityQueue<>(new AStarNodeComparator());
    priorityQueue.addAll(openNodes);
    return priorityQueue.poll();
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
