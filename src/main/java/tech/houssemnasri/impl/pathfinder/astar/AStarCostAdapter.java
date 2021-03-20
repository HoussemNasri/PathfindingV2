package tech.houssemnasri.impl.pathfinder.astar;

import tech.houssemnasri.PathCost;
import tech.houssemnasri.api.pathfinder.cost.IAstarCost;

public class AStarCostAdapter implements IAstarCost {
    private final PathCost costInfo;

    public AStarCostAdapter(PathCost costInfo) {
        this.costInfo = costInfo;
        updateFCost();
    }

    private void updateFCost() {
        costInfo.getCostArguments().set(0, fCost());
    }

    @Override
    public void setG(int newGCost) {
        costInfo.getCostArguments().set(1, newGCost);
        updateFCost();
    }

    @Override
    public int gCost() {
        return costInfo.getCostArguments().get(1);
    }

    @Override
    public void setH(int newHCost) {
        costInfo.getCostArguments().set(2, newHCost);
        updateFCost();
    }

    @Override
    public int hCost() {
        return costInfo.getCostArguments().get(2);
    }

    public PathCost getCostEntity() {
        return costInfo;
    }

    @Override
    public int compare(IAstarCost o1, IAstarCost o2) {
        if (o1.fCost() != o2.fCost()) {
            return Integer.compare(o1.fCost(), o2.fCost());
        }
        return Integer.compare(o1.hCost(), o2.hCost());
    }
}
