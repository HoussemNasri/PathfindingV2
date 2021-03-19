package tech.houssemnasri.impl.pathfinder.astar;

import tech.houssemnasri.CostEntity;
import tech.houssemnasri.api.pathfinder.cost.IAstarCost;

public class AstarCostAdapter implements IAstarCost {
    private final CostEntity costInfo;

    public AstarCostAdapter(CostEntity costInfo) {
        this.costInfo = costInfo;
        setFCost();
    }

    private void setFCost() {
        costInfo.getCostArguments().set(2, fCost());
    }

    @Override
    public void updateGCost(int newGCost) {
        costInfo.getCostArguments().set(0, newGCost);
    }

    @Override
    public int gCost() {
        return costInfo.getCostArguments().get(0);
    }

    @Override
    public void updateHCost(int newHCost) {
        costInfo.getCostArguments().set(1, newHCost);
    }

    @Override
    public int hCost() {
        return costInfo.getCostArguments().get(1);
    }

    public CostEntity getCostEntity() {
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
