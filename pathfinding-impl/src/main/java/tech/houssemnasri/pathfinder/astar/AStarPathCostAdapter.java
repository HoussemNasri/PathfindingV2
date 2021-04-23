package tech.houssemnasri.pathfinder.astar;

import tech.houssemnasri.pathfinder.AlgorithmStep;
import tech.houssemnasri.command.UpdateCostCommand;
import tech.houssemnasri.pathfinder.cost.PathCost;
import tech.houssemnasri.pathfinder.cost.IAStarCost;

public class AStarPathCostAdapter implements IAStarCost {
  private final PathCost costInfo;
  private final AlgorithmStep algorithmStep;

  public AStarPathCostAdapter(PathCost costInfo, AlgorithmStep algorithmStep) {
    this.costInfo = costInfo;
    this.algorithmStep = algorithmStep;
    setF();
  }

  public AStarPathCostAdapter(PathCost costInfo) {
    this(costInfo, new AlgorithmStep());
  }

  private void setF() {
    algorithmStep.pushAndExecute(new UpdateCostCommand(costInfo, 0, fCost()));
  }

  @Override
  public void setG(int newGCost) {
    algorithmStep.pushAndExecute(new UpdateCostCommand(costInfo, 1, newGCost));
    setF();
  }

  @Override
  public int gCost() {
    return costInfo.getCostArguments().get(1);
  }

  @Override
  public void setH(int newHCost) {
    algorithmStep.pushAndExecute(new UpdateCostCommand(costInfo, 2, newHCost));
    setF();
  }

  @Override
  public int hCost() {
    return costInfo.getCostArguments().get(2);
  }

  @Override
  public int compareTo(IAStarCost o) {
    if (fCost() != o.fCost()) {
      return Integer.compare(this.fCost(), o.fCost());
    }
    return Integer.compare(this.hCost(), o.hCost());
  }
}
