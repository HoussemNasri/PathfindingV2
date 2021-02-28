package tech.houssemnasri.impl;

import javafx.beans.property.ObjectProperty;

import tech.houssemnasri.api.CostEntity;
import tech.houssemnasri.api.INode;
import tech.houssemnasri.api.IPosition;
import tech.houssemnasri.property.ComplexObjectProperty;

public final class PNode implements INode {
    /** This is the position of this node on the grid */
    private IPosition position;
    /**
     * This is the type of the node.
     *
     * @see Type
     */
    private final ObjectProperty<Type> typeProperty = new ComplexObjectProperty<>();
    /**
     * This is the parent of the node, some pathfinding algorithms use a parent node inside each
     * node to backtrack the path once it's found.
     */
    private INode parent;

    /**
     * The cost data of the node, algorithms like A* choose the best or shortest path by calculating
     * a cost and choosing the smallest cost value.
     */
    private final ObjectProperty<CostEntity> costProperty = new ComplexObjectProperty<>();

    public PNode(IPosition position, Type type, INode parent, CostEntity costEntity) {
        setPosition(position);
        setType(type);
        setParent(parent);
        setCostEntity(costEntity);
    }

    public PNode(IPosition position, Type type, INode parent) {
        this(position, type, parent, NO_COST);
    }

    public PNode(IPosition position, Type type) {
        this(position, type, null);
    }

    public PNode(IPosition position) {
        this(position, Type.BASIC);
    }

    private void setPosition(IPosition position) {
        this.position = position;
    }

    @Override
    public IPosition getPosition() {
        return position;
    }

    @Override
    public void setType(Type type) {
        typeProperty.set(type);
    }

    @Override
    public Type getType() {
        return typeProperty.get();
    }

    @Override
    public ObjectProperty<Type> nodeTypeProperty() {
        return typeProperty;
    }

    @Override
    public void setParent(INode parent) {
        this.parent = parent;
    }

    @Override
    public INode getParent() {
        return parent;
    }

    @Override
    public void setCostEntity(CostEntity costEntity) {
        costProperty.set(costEntity);
    }

    @Override
    public CostEntity getCostEntity() {
        return costProperty.get();
    }

    @Override
    public ObjectProperty<CostEntity> nodeCostProperty() {
        return costProperty;
    }

    @Override
    public String toString() {
        return String.format("PNode{%s, %s}", getPosition(), getType());
    }
}
