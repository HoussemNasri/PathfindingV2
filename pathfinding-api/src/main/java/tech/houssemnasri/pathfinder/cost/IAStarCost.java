package tech.houssemnasri.pathfinder.cost;

public interface IAStarCost extends Comparable<IAStarCost> {
  /** Sets the g cost to {@code newGCost} */
  void setG(int newGCost);
  /** Returns the cost from the start node to the current node. */
  int gCost();
  /** Sets the h cost to {@code newHCost} */
  void setH(int newHCost);
  /** Returns estimated cost from current node to goal. */
  int hCost();
  /**
   * The evaluation function.
   *
   * @return The sum of {@code gCost()} and {@code hCost()}
   */
  default int fCost() {
    return gCost() + hCost();
  }
}
