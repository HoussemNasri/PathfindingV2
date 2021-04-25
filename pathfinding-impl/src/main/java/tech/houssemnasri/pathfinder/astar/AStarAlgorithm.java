package tech.houssemnasri.pathfinder.astar;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import tech.houssemnasri.node.Position;
import tech.houssemnasri.command.AlgorithmCommandContext;
import tech.houssemnasri.grid.IGrid;
import tech.houssemnasri.node.INode;
import tech.houssemnasri.pathfinder.BaseAlgorithm;
import tech.houssemnasri.pathfinder.Distance;
import tech.houssemnasri.pathfinder.cost.IAStarCost;
import tech.houssemnasri.command.CloseNodeCommand;
import tech.houssemnasri.command.OpenNodeCommand;
import tech.houssemnasri.command.SetCurrentNodeCommand;
import tech.houssemnasri.command.SetParentCommand;
import tech.houssemnasri.pathfinder.AlgorithmStep;
import tech.houssemnasri.pathfinder.distance.ManhattanDistance;

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
      Position source = grid.getSourcePosition();
      if (not(closedNodes.contains(grid.getNode(source)))) {
        new OpenNodeCommand(this, step, grid.getNode(source)).execute();
        estimateDistanceToDestination(grid.getNode(source), step);
        new AStarPathCostAdapter(getGrid().getNode(source).getPathCost(), step).setG(0);
      } else {
        // We are stuck.
        System.out.println("We are stuck!");
        return finalize(step);
      }
    }
    new SetCurrentNodeCommand(this, step, getLeastCostNode()).execute();
    new CloseNodeCommand(this, step, getCurrentNode()).execute();

    if (getGrid().isDestinationNode(getCurrentNode())) {
      return finalize(step);
    }

    List<INode> neighbors = getCurrentNodeNeighbors();
    IAStarCost currentNodeCost = new AStarPathCostAdapter(getCurrentNode().getPathCost(), step);
    for (INode nei : neighbors) {
      if (isVisited(nei)) {
        continue;
      }
      IAStarCost neighborNodeCost = new AStarPathCostAdapter(nei.getPathCost(), step);
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
    Position thisPosition = node.getPosition();
    Position destPosition = grid.getDestinationPosition();
    Distance distance = new ManhattanDistance();
    new AStarPathCostAdapter(node.getPathCost(), step)
        .setH(distance.apply(thisPosition, destPosition));
  }

  private INode getLeastCostNode() {
    PriorityQueue<INode> priorityQueue = new PriorityQueue<>(new AStarNodeComparator());
    priorityQueue.addAll(openNodes);
    return priorityQueue.poll();
  }

  @Override
  public Set<INode> getUnvisitedNodes() {
    return openNodes;
  }

  @Override
  public Set<INode> getVisitedNodes() {
    return closedNodes;
  }
}
