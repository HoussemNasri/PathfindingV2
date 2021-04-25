package tech.houssemnasri.pathfinder.dijkstra;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import tech.houssemnasri.node.Position;
import tech.houssemnasri.command.AlgorithmCommandContext;
import tech.houssemnasri.grid.IGrid;
import tech.houssemnasri.node.INode;
import tech.houssemnasri.pathfinder.AlgorithmStep;
import tech.houssemnasri.pathfinder.BaseAlgorithm;
import tech.houssemnasri.pathfinder.cost.IDijkstraPathCost;
import tech.houssemnasri.command.CloseNodeCommand;
import tech.houssemnasri.command.OpenNodeCommand;
import tech.houssemnasri.command.SetCurrentNodeCommand;
import tech.houssemnasri.command.SetParentCommand;

public class DijkstraAlgorithm extends BaseAlgorithm {
  private final Set<INode> unvisited = new HashSet<>();
  private final Set<INode> visited = new HashSet<>();

  public DijkstraAlgorithm(IGrid grid, boolean isDiagonalAllowed) {
    super(grid, isDiagonalAllowed);
  }

  public DijkstraAlgorithm(IGrid grid) {
    super(grid, false);
  }

  @Override
  protected AlgorithmStep advance() {
    AlgorithmStep step = new AlgorithmStep();
    if (unvisited.isEmpty()) {
      Position source = grid.getSourcePosition();
      if (not(visited.contains(grid.getNode(source)))) {
        new OpenNodeCommand(this, step, grid.getNode(source)).execute();
        setDistanceFromSource(getGrid().getNode(source), 0);
      } else {
        // We are stuck.
        System.out.println("We are stuck!");
        return step;
      }
    }
    INode leastCostNode = getLeastCostNode();
    new SetCurrentNodeCommand(this, step, leastCostNode).execute();
    new CloseNodeCommand(this, step, leastCostNode).execute();

    if (getGrid().isDestinationNode(getCurrentNode())) {
      step.markAsFinal();
      return step;
    }

    List<INode> neighbors = getCurrentNodeNeighbors();
    IDijkstraPathCost currentNodeCost =
        new DijkstraPathCostAdapter(getCurrentNode().getPathCost(), step);
    for (INode nei : neighbors) {
      if (isVisited(nei)) {
        continue;
      }
      IDijkstraPathCost neighborNodeCost = new DijkstraPathCostAdapter(nei.getPathCost(), step);
      AlgorithmCommandContext neighborsContext = AlgorithmCommandContext.create(this, step, nei);
      int distanceForCurrent = currentNodeCost.getShortestDistanceFromSourceNode();
      int distanceForNeighbor = neighborNodeCost.getShortestDistanceFromSourceNode();
      int distanceForNeighborUpdate =
          distanceForCurrent + (isOnDiagonal(nei) ? DIAGONAL_DISTANCE : HORIZ_VERT_DISTANCE);
      if (isNodeOpen(nei)) {
        if (distanceForNeighbor > distanceForNeighborUpdate) {
          setDistanceFromSource(nei, distanceForNeighborUpdate);
          new SetParentCommand(neighborsContext, currentNode).execute();
        }
      } else {
        setDistanceFromSource(nei, distanceForNeighborUpdate);
        new SetParentCommand(neighborsContext, currentNode).execute();
        new OpenNodeCommand(neighborsContext).execute();
      }
    }
    return step;
  }

  private INode getLeastCostNode() {
    PriorityQueue<INode> priorityQueue = new PriorityQueue<>(new DijkstraNodeComparator());
    priorityQueue.addAll(unvisited);
    return priorityQueue.poll();
  }

  private void setDistanceFromSource(INode node, int distance) {
    IDijkstraPathCost dijkstraPathCost = new DijkstraPathCostAdapter(node.getPathCost());
    dijkstraPathCost.setShortestDistanceFromSourceNode(distance);
  }

  @Override
  public Set<INode> getUnvisitedNodes() {
    return unvisited;
  }

  @Override
  public Set<INode> getVisitedNodes() {
    return visited;
  }
}
