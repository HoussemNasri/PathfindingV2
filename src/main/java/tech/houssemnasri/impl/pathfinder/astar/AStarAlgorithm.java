package tech.houssemnasri.impl.pathfinder.astar;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import tech.houssemnasri.api.command.AlgorithmCommandContext;
import tech.houssemnasri.api.grid.IGrid;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.node.IPosition;
import tech.houssemnasri.api.pathfinder.BaseAlgorithm;
import tech.houssemnasri.api.pathfinder.Distance;
import tech.houssemnasri.api.pathfinder.cost.IAStarCost;
import tech.houssemnasri.impl.command.CloseNodeCommand;
import tech.houssemnasri.impl.command.OpenNodeCommand;
import tech.houssemnasri.impl.command.SetCurrentNodeCommand;
import tech.houssemnasri.impl.command.SetParentCommand;
import tech.houssemnasri.impl.pathfinder.AlgorithmStep;
import tech.houssemnasri.impl.pathfinder.distance.ManhattanDistance;

/** A* implementation */
public class AStarAlgorithm extends BaseAlgorithm {
  private final Set<INode> openNodes = new HashSet<>();
  private final Set<INode> closedNodes = new HashSet<>();

  public AStarAlgorithm(IGrid grid, boolean isDiagonalAllowed) {
    super(grid, isDiagonalAllowed);
  }

  public AStarAlgorithm(IGrid grid) {
    super(grid, false);
  }

  @Override
  public AlgorithmStep advance() {
    AlgorithmStep step = new AlgorithmStep();
    if (openNodes.isEmpty()) {
      IPosition source = grid.getSourcePosition();
      new OpenNodeCommand(this, step, grid.getNode(source)).execute();
      estimateDistanceToDestination(grid.getNode(source), step);
      new AStarCostAdapter(getGrid().getNode(source).getPathCost(), step).setG(0);
    }
    new SetCurrentNodeCommand(this, step, getLeastCostNode()).execute();
    new CloseNodeCommand(this, step, getCurrentNode()).execute();

    if (getGrid().isDestinationNode(getCurrentNode())) {
      step.markAsFinal();
      return step;
    }

    List<INode> neighbors = getCurrentNodeNeighbors();
    IAStarCost currentNodeCost = new AStarCostAdapter(getCurrentNode().getPathCost(), step);
    for (INode nei : neighbors) {
      if (isNodeClosed(nei)) {
        continue;
      }
      IAStarCost neighborNodeCost = new AStarCostAdapter(nei.getPathCost(), step);
      AlgorithmCommandContext neighborsContext = AlgorithmCommandContext.create(this, step, nei);
      int gCostForCurrent = currentNodeCost.gCost();
      int gCostForNeighbor = neighborNodeCost.gCost();
      int gCostForNeighborUpdate =
          gCostForCurrent + (isOnDiagonal(nei) ? DIAGONAL_DISTANCE : HORIZ_VERT_DISTANCE);
      if (isNodeOpen(nei)) {
        if (gCostForNeighbor > gCostForNeighborUpdate) {
          neighborNodeCost.setG(gCostForNeighborUpdate);
          new SetParentCommand(neighborsContext, currentNode).execute();
        }
      } else {
        neighborNodeCost.setG(gCostForNeighborUpdate);
        new SetParentCommand(neighborsContext, currentNode).execute();
        estimateDistanceToDestination(nei, step);
        new OpenNodeCommand(neighborsContext).execute();
      }
    }
    return step;
  }

  private void estimateDistanceToDestination(INode node, AlgorithmStep step) {
    IPosition thisPosition = node.getPosition();
    IPosition destPosition = grid.getDestinationPosition();
    Distance distance = new ManhattanDistance();
    new AStarCostAdapter(node.getPathCost(), step)
        .setH(distance.apply(thisPosition, destPosition));
  }

  private INode getLeastCostNode() {
    PriorityQueue<INode> priorityQueue = new PriorityQueue<>(new AStarNodeComparator());
    priorityQueue.addAll(openNodes);
    return priorityQueue.poll();
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
