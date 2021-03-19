package tech.houssemnasri.api.node;

import javafx.beans.property.ObjectProperty;

import tech.houssemnasri.CostEntity;

public interface INode {
    /** constant for no cost */
    CostEntity NO_COST = null;

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

    /** Reset the node to it's initial state as a basic node with no parent and no cost arguments */
    void clear();

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
        PATH,
        SOURCE,
        DESTINATION
    }
}
