package tech.houssemnasri.impl.pathfinder.astar;

import tech.houssemnasri.api.pathfinder.AlgorithmStep;
import tech.houssemnasri.impl.command.UpdateCostCommand;
import tech.houssemnasri.impl.pathfinder.PathCost;
import tech.houssemnasri.api.pathfinder.cost.IAStarCost;

public class AStarCostAdapter implements IAStarCost {
  private final PathCost costInfo;
  private final AlgorithmStep algorithmStep;

  public AStarCostAdapter(PathCost costInfo, AlgorithmStep algorithmStep) {
    this.costInfo = costInfo;
    this.algorithmStep = algorithmStep;
    setF();
  }

  public AStarCostAdapter(PathCost costInfo) {
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
