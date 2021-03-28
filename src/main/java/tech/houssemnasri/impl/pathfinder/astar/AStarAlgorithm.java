package tech.houssemnasri.impl.pathfinder.astar;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

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
    AlgorithmStep algorithmStep = new AlgorithmStep();
    if (openNodes.isEmpty()) {
      IPosition source = grid.getSourcePosition();
      algorithmStep.exec(new OpenNodeCommand(this, grid.getNode(source)));
      computeHCostFor(grid.getNode(source), algorithmStep);
      new AStarCostAdapter(getGrid().getNode(source).getPathCost(), algorithmStep).setG(0);
    }
    algorithmStep.exec(new SetCurrentNodeCommand(this, getLeastCostNode()));
    algorithmStep.exec(new CloseNodeCommand(this, getCurrentNode()));

    if (getGrid().isDestinationNode(getCurrentNode())) {
      recordStep(algorithmStep);
      doTraceBackPath();
      setPathFound(true);
      return;
    }

    List<INode> neighbors = getCurrentNodeNeighbors();
    IAStarCost currentNodeCost =
        new AStarCostAdapter(getCurrentNode().getPathCost(), algorithmStep);
    for (INode nei : neighbors) {
      if (isNodeClosed(nei)) {
        continue;
      }
      IAStarCost neighborNodeCost = new AStarCostAdapter(nei.getPathCost(), algorithmStep);
      int gCostForCurrent = currentNodeCost.gCost();
      int gCostForNeighbor = neighborNodeCost.gCost();
      int gCostForNeighborUpdate =
          gCostForCurrent + (isOnDiagonal(nei) ? DIAGONAL_DISTANCE : HORIZ_VERT_DISTANCE);
      if (isNodeOpen(nei)) {
        if (gCostForNeighbor > gCostForNeighborUpdate) {
          neighborNodeCost.setG(gCostForNeighborUpdate);
          algorithmStep.exec(new SetParentCommand(this, nei, currentNode));
        }
      } else {
        neighborNodeCost.setG(gCostForNeighborUpdate);
        algorithmStep.exec(new SetParentCommand(this, nei, currentNode));
        computeHCostFor(nei, algorithmStep);
        algorithmStep.exec(new OpenNodeCommand(this, nei));
      }
    }
    recordStep(algorithmStep);
  }

  private void computeHCostFor(INode node, AlgorithmStep algorithmStep) {
    IPosition thisPosition = node.getPosition();
    IPosition destPosition = grid.getDestinationPosition();
    Distance distance = new ManhattanDistance();
    new AStarCostAdapter(node.getPathCost(), algorithmStep)
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
