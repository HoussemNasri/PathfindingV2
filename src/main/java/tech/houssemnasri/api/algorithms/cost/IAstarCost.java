package tech.houssemnasri.api.algorithms.cost;

import java.util.Comparator;

public interface IAstarCost extends Comparator<IAstarCost> {
    void updateGCost(int newGCost);
    /** Returns the cost from the start node to the current node. */
    int gCost();
    void updateHCost(int newHCost);
    /** Returns estimated cost from current node to goal. */
    int hCost();
    /**
     * The evaluation function.
     *
     * @return The sum of {@code gCost()} and {@code hCost()}
     */
    default int fCost() {
        return gCost() + hCost();
    }
}
