package tech.houssemnasri.impl.pathfinder.astar;

import java.util.Comparator;

import tech.houssemnasri.api.pathfinder.cost.IAStarCost;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.impl.pathfinder.dijkstra.DijkstraNodeComparator;

public class AStarNodeComparator implements Comparator<INode> {
  @Override
  public int compare(INode o1, INode o2) {
    IAStarCost o1Cost = new AStarPathCostAdapter(o1.getPathCost());
    IAStarCost o2Cost = new AStarPathCostAdapter(o2.getPathCost());
    //return Integer.compare(o1Cost.gCost(), o2Cost.gCost());
    return o1Cost.compareTo(o2Cost);
  }
}
