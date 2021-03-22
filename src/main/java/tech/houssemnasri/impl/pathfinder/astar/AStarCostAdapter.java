package tech.houssemnasri.impl.pathfinder.astar;

import tech.houssemnasri.impl.command.CommandRecord;
import tech.houssemnasri.impl.command.UpdateCostCommand;
import tech.houssemnasri.impl.pathfinder.PathCost;
import tech.houssemnasri.api.pathfinder.cost.IAStarCost;

public class AStarCostAdapter implements IAStarCost {
  private final PathCost costInfo;
  private final CommandRecord commandRecord;

  public AStarCostAdapter(PathCost costInfo, CommandRecord commandRecord) {
    this.costInfo = costInfo;
    this.commandRecord = commandRecord;
    setF();
  }

  public AStarCostAdapter(PathCost costInfo) {
    this(costInfo, new CommandRecord());
  }

  private void setF() {
    commandRecord.push(new UpdateCostCommand(costInfo, 0, fCost()).executeAndReturn());
  }

  @Override
  public void setG(int newGCost) {
    commandRecord.push(new UpdateCostCommand(costInfo, 1, newGCost).executeAndReturn());
    setF();
  }

  @Override
  public int gCost() {
    return costInfo.getCostArguments().get(1);
  }

  @Override
  public void setH(int newHCost) {
    commandRecord.push(new UpdateCostCommand(costInfo, 2, newHCost).executeAndReturn());
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
