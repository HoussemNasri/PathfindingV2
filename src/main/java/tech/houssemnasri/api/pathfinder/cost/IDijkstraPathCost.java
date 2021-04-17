package tech.houssemnasri.api.pathfinder.cost;

public interface IDijkstraPathCost {
  int getShortestDistanceFromSourceNode();

  void setShortestDistanceFromSourceNode(int distance);
}
