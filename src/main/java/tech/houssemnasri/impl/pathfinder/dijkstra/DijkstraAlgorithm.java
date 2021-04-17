package tech.houssemnasri.impl.pathfinder.dijkstra;

import java.util.HashSet;
import java.util.Set;

import tech.houssemnasri.api.grid.IGrid;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.pathfinder.AlgorithmStep;
import tech.houssemnasri.api.pathfinder.BaseAlgorithm;

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
    System.out.println("Executing...");
    return step;
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
