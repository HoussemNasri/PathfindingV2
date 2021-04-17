package tech.houssemnasri.api.pathfinder.cost;

import tech.houssemnasri.api.node.INode;

public interface IDijkstraPathCost{
  int getShortestDistanceFromSourceNode();

  void setShortestDistanceFromSourceNode(int distance);
}
