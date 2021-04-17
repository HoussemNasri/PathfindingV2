package tech.houssemnasri.impl.pathfinder.dijkstra;

import java.util.Comparator;

import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.pathfinder.cost.IDijkstraPathCost;

public class DijkstraNodeComparator implements Comparator<INode> {
  @Override
  public int compare(INode o1, INode o2) {
    IDijkstraPathCost o1Cost = new DijkstraPathCostAdapter(o1.getPathCost());
    IDijkstraPathCost o2Cost = new DijkstraPathCostAdapter(o2.getPathCost());
    return Integer.compare(o1Cost.getShortestDistanceFromSourceNode(), o2Cost.getShortestDistanceFromSourceNode());
  }
}
