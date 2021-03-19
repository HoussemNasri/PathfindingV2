package tech.houssemnasri;

import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The {@code CostEntity} class holds the cost information of a node, some algorithms use cost
 * values to determine the shortest path between a set of nodes
 */
public final class CostEntity implements Cloneable {
    /**
     * We encourage creating a new {@code CostEntity} object every time cost values change rather
     * than using an exiting one, this will help us listen for cost changes
     */
    private final ObservableList<Integer> costArguments;

    public CostEntity(final ObservableList<Integer> costArguments) {
        this.costArguments = costArguments;
    }

    /** Returns a list of calculated costs */
    public ObservableList<Integer> getCostArguments() {
        return costArguments;
    }

    @Override
    public String toString() {
        return "CostEntity{" + "costArguments=" + costArguments + '}';
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return new CostEntity(FXCollections.observableArrayList(getCostArguments()));
        }
    }
}
