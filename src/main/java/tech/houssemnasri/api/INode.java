package tech.houssemnasri.api;

import javafx.beans.property.ObjectProperty;

import tech.houssemnasri.impl.PNode;

public interface INode {
    /** constant for no cost */
    CostEntity NO_COST = new CostEntity(new int[0]);

    IPosition getPosition();

    /**
     * Assigning a new type to this node
     *
     * @see Type
     */
    void setType(Type nodeType);

    /**
     * Returns the current type of this node
     *
     * @see Type
     */
    Type getType();

    ObjectProperty<Type> nodeTypeProperty();

    void setParent(INode parent);

    INode getParent();

    void setCostEntity(CostEntity costEntity);

    CostEntity getCostEntity();

    ObjectProperty<CostEntity> nodeCostProperty();

    /**
     * The type of node helps the UI module to draw nodes with custom styling, it also beneficial
     * for the algorithm module to decide which node to explore as that some nodes are not
     * explorable like {@code Type.WALL}.
     */
    enum Type {
        BASIC,
        WALL,
        OPEN,
        CLOSED,
        PATH
    }
}
