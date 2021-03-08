package tech.houssemnasri;

import java.util.Arrays;

/**
 * The {@code CostEntity} class holds the cost information of a node, some algorithms use cost
 * values to determine the shortest path between a set of nodes
 */
public final class CostEntity {
    /**
     * We encourage creating a new {@code CostEntity} object every time cost values change rather
     * than using an exiting one, this will help us listen for cost changes
     */
    private final int[] costArguments;

    public CostEntity(final int[] costArguments) {
        this.costArguments = costArguments;
    }

    /** Returns a list of calculated costs */
    public int[] getCostArguments() {
        return costArguments;
    }

    @Override
    public String toString() {
        return "CostEntity{" +
                "costArguments=" + Arrays.toString(costArguments) +
                '}';
    }
}
