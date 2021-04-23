package tech.houssemnasri.command;

import javafx.collections.ObservableList;

import tech.houssemnasri.pathfinder.cost.PathCost;

public class UpdateCostCommand implements ICommand {
  private final PathCost pathCost;
  private final int position;
  private final int value;
  private int prevValue;

  public UpdateCostCommand(PathCost pathCost, int position, int value) {
    this.pathCost = pathCost;
    this.position = position;
    this.value = value;
    setPreviousValue();
  }

  private void setPreviousValue() {
    ObservableList<Integer> arguments = pathCost.getCostArguments();
    prevValue = arguments.size() > position ? arguments.get(position) : 0;
  }

  @Override
  public void execute() {
    pathCost.getCostArguments().set(position, value);
  }

  @Override
  public void undo() {
    pathCost.getCostArguments().set(position, prevValue);
  }
}
