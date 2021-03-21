package tech.houssemnasri.impl.pathfinder.astar;

import tech.houssemnasri.impl.command.CommandRecord;
import tech.houssemnasri.impl.command.UpdateCostCommand;
import tech.houssemnasri.impl.pathfinder.PathCost;
import tech.houssemnasri.api.pathfinder.cost.IAstarCost;

public class AStarCostAdapter implements IAstarCost {
  private final PathCost costInfo;
  private final CommandRecord commandRecord;

  public AStarCostAdapter(PathCost costInfo, CommandRecord commandRecord) {
    this.costInfo = costInfo;
    this.commandRecord = commandRecord;
    updateFCost();
  }

  public AStarCostAdapter(PathCost costInfo){
    this(costInfo, new CommandRecord());
  }

  private void updateFCost() {
    commandRecord.push(new UpdateCostCommand(costInfo, 0, fCost()).executeAndReturn());
  }

  @Override
  public void setG(int newGCost) {
    commandRecord.push(new UpdateCostCommand(costInfo, 1, newGCost).executeAndReturn());
    updateFCost();
  }

  @Override
  public int gCost() {
    return costInfo.getCostArguments().get(1);
  }

  @Override
  public void setH(int newHCost) {
    commandRecord.push(new UpdateCostCommand(costInfo, 2, newHCost).executeAndReturn());
    updateFCost();
  }

  @Override
  public int hCost() {
    return costInfo.getCostArguments().get(2);
  }

  @Override
  public int compare(IAstarCost o1, IAstarCost o2) {
    if (o1.fCost() != o2.fCost()) {
      return Integer.compare(o1.fCost(), o2.fCost());
    }
    return Integer.compare(o1.hCost(), o2.hCost());
  }
}
