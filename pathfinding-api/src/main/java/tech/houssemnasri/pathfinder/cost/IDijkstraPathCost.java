package tech.houssemnasri.pathfinder.cost;

import tech.houssemnasri.node.INode;

public interface IDijkstraPathCost{
  int getShortestDistanceFromSourceNode();

  void setShortestDistanceFromSourceNode(int distance);
}
