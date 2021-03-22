package tech.houssemnasri.impl.pathfinder.astar;

import java.util.Comparator;

import tech.houssemnasri.api.pathfinder.cost.IAstarCost;
import tech.houssemnasri.api.node.INode;

public class AStarNodeComparator implements Comparator<INode> {
  @Override
  public int compare(INode o1, INode o2) {
    IAstarCost o1Cost = new AStarCostAdapter(o1.getPathCost());
    IAstarCost o2Cost = new AStarCostAdapter(o2.getPathCost());
    return o1Cost.compareTo(o2Cost);
  }
}
