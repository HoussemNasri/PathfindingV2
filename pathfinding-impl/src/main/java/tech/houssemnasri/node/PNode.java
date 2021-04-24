package tech.houssemnasri.node;

import javafx.beans.property.ObjectProperty;

import tech.houssemnasri.pathfinder.cost.PathCost;
import tech.houssemnasri.property.ComplexObjectProperty;

public final class PNode implements INode {
  public static final INode NULL = new PNode(Position.ERROR, Type.BASIC, null, null);
  /** This is the position of this node on the grid */
  private Position position;
  /**
   * This is the type of the node.
   *
   * @see Type
   */
  private final ObjectProperty<Type> typeProperty = new ComplexObjectProperty<>();
  /**
   * This is the parent of the node, some pathfinding algorithms use a parent node inside each node
   * to backtrack the path once it's found.
   */
  private INode parent;

  /**
   * The cost data of the node, algorithms like A* choose the best or shortest path by calculating a
   * cost and choosing the smallest cost value.
   */
  private final ObjectProperty<PathCost> costProperty = new ComplexObjectProperty<>();

  public PNode(Position position, Type type, INode parent, PathCost pathCost) {
    setPosition(position);
    setType(type);
    setParent(parent);
    setPathCost(pathCost);
  }

  public PNode(Position position, Type type, INode parent) {
    this(position, type, parent, new PathCost());
  }

  public PNode(Position position, Type type) {
    this(position, type, null);
  }

  public PNode(Position position) {
    this(position, Type.BASIC);
  }

  public PNode(INode node) {
    setPosition(Position.of(node.getPosition().getX(), node.getPosition().getY()));
    setType(node.getType());
    setParent(node.getParent() == null ? null : new PNode(node.getParent()));
    setPathCost(new PathCost(node.getPathCost()));
  }

  private void setPosition(Position position) {
    this.position = position;
  }

  @Override
  public Position getPosition() {
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

  private void setPathCost(PathCost pathCost) {
    costProperty.set(pathCost);
  }

  @Override
  public PathCost getPathCost() {
    return costProperty.get();
  }

  @Override
  public ObjectProperty<PathCost> nodeCostProperty() {
    return costProperty;
  }

  @Override
  public void clear() {
    setParent(null);
    getPathCost().clear();
  }

  @Override
  public String toString() {
    return String.format("PNode{%s, %s}", getPosition(), getType());
  }
}
