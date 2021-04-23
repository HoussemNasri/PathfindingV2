package tech.houssemnasri.pathfinder.dijkstra;

import java.util.Comparator;

import tech.houssemnasri.node.INode;
import tech.houssemnasri.pathfinder.cost.IDijkstraPathCost;

public class DijkstraNodeComparator implements Comparator<INode> {
  @Override
  public int compare(INode o1, INode o2) {
    IDijkstraPathCost o1Cost = new DijkstraPathCostAdapter(o1.getPathCost());
    IDijkstraPathCost o2Cost = new DijkstraPathCostAdapter(o2.getPathCost());
    return Integer.compare(o1Cost.getShortestDistanceFromSourceNode(), o2Cost.getShortestDistanceFromSourceNode());
  }
}
