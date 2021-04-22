package tech.houssemnasri.impl.pathfinder;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The {@code PathCost} class holds the cost information of a node, some algorithms use cost values
 * to determine the shortest path between a set of nodes
 */
public final class PathCost implements Cloneable {
  /**
   * We encourage creating a new {@code CostEntity} object every time cost values change rather than
   * using an exiting one, this will help us listen for cost changes
   */
  private final ObservableList<Integer> costArguments;

  private final List<Listener> listeners = new ArrayList<>();

  public PathCost(final ObservableList<Integer> costArguments) {
    this.costArguments = costArguments;
  }

  public PathCost() {
    this(FXCollections.observableArrayList(0, 0, 0));
  }

  public PathCost(PathCost pathCost) {
    this(FXCollections.observableArrayList(pathCost.getCostArguments()));
  }

  /** Returns a list of calculated costs */
  public ObservableList<Integer> getCostArguments() {
    return costArguments;
  }

  public void clear() {
    costArguments.setAll(0, 0, 0);
    notifyClearedListeners();
  }

  @Override
  public String toString() {
    return "CostEntity{" + "costArguments=" + costArguments + '}';
  }

  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      return new PathCost(FXCollections.observableArrayList(getCostArguments()));
    }
  }

  public void registerListener(Listener li) {
    listeners.add(li);
  }

  public void unregisterListener(Listener li) {
    listeners.remove(li);
  }

  private void notifyClearedListeners() {
    listeners.forEach(Listener::onCostCleared);
  }

  public interface Listener {
    void onCostCleared();
  }
}
