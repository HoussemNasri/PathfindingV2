package tech.houssemnasri.pathfinder.dijkstra;

import tech.houssemnasri.pathfinder.AlgorithmStep;
import tech.houssemnasri.pathfinder.cost.IDijkstraPathCost;
import tech.houssemnasri.command.UpdateCostCommand;
import tech.houssemnasri.pathfinder.cost.PathCost;

public class DijkstraPathCostAdapter implements IDijkstraPathCost {
  private final PathCost pathCost;
  private final AlgorithmStep algorithmStep;

  public DijkstraPathCostAdapter(PathCost pathCost, AlgorithmStep algorithmStep) {
    this.pathCost = pathCost;
    this.algorithmStep = algorithmStep;
  }

  public DijkstraPathCostAdapter(PathCost costInfo) {
    this(costInfo, new AlgorithmStep());
  }

  @Override
  public int getShortestDistanceFromSourceNode() {
    return pathCost.getCostArguments().get(0);
  }

  @Override
  public void setShortestDistanceFromSourceNode(int distance) {
    algorithmStep.pushAndExecute(new UpdateCostCommand(pathCost, 0, distance));
  }
}
