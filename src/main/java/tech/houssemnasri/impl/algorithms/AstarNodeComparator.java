package tech.houssemnasri.impl.algorithms;

import java.util.Comparator;

import tech.houssemnasri.api.algorithms.cost.IAstarCost;
import tech.houssemnasri.api.node.INode;

public class AstarNodeComparator implements Comparator<INode> {
    @Override
    public int compare(INode o1, INode o2) {
        IAstarCost o1Cost = new AstarCostAdapter(o1.getCostEntity());
        IAstarCost o2Cost = new AstarCostAdapter(o2.getCostEntity());
        return o1Cost.compare(o1Cost, o2Cost);
    }
}
