package tech.houssemnasri.pathfinder.astar;

import java.util.Comparator;

import tech.houssemnasri.node.INode;
import tech.houssemnasri.pathfinder.cost.IAStarCost;

public class AStarNodeComparator implements Comparator<INode> {
  @Override
  public int compare(INode o1, INode o2) {
    IAStarCost o1Cost = new AStarPathCostAdapter(o1.getPathCost());
    IAStarCost o2Cost = new AStarPathCostAdapter(o2.getPathCost());
    return o1Cost.compareTo(o2Cost);
  }
}
