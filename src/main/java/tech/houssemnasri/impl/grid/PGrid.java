package tech.houssemnasri.impl.grid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

import tech.houssemnasri.BooleanExtensions;
import tech.houssemnasri.util.GridChecker;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.grid.IGrid;
import tech.houssemnasri.api.node.IPosition;
import tech.houssemnasri.impl.node.PNode;
import tech.houssemnasri.impl.node.Position;
import tech.houssemnasri.property.ComplexIntegerProperty;
import tech.houssemnasri.property.ComplexObjectProperty;
import static tech.houssemnasri.api.node.INode.*;

/**
 * The {@code PGrid} class represent the grid state, it keeps all state variables in one place,
 * PGrid is a singleton object because with the current app state there is no reason to have more
 * than one.
 */
public final class PGrid implements IGrid, Serializable, BooleanExtensions {
  private static PGrid INSTANCE = null;

  private final PNode[][] nodes;
  /** This is the number of rows specified for the grid. */
  private final IntegerProperty rowsProperty = new ComplexIntegerProperty();
  /** This is the number of columns specified for the grid. */
  private final IntegerProperty colsProperty = new ComplexIntegerProperty();

  /** This is the position of the source node in the grid */
  private final ObjectProperty<IPosition> sourcePositionProperty = new ComplexObjectProperty<>();
  /** This is the position of the destination node in the grid */
  private final ObjectProperty<IPosition> destinationPositionProperty =
      new ComplexObjectProperty<>();

  private PGrid(int rows, int cols, IPosition sourcePosition, IPosition destinationPosition) {
    setRows(rows);
    setColumns(cols);
    setSourcePosition(sourcePosition);
    setDestinationPosition(destinationPosition);
    nodes = new PNode[getColumns()][getRows()];
    createNodes();
  }

  private PGrid(int rows, int cols) {
    this(rows, cols, Position.of(10, 10), Position.of(18, 10));
  }

  /** create and initialize the nodes based on the current state */
  private void createNodes() {
    for (int x = 0; x < getColumns(); x++) {
      for (int y = 0; y < getRows(); y++) {
        Position newNodePosition = Position.of(x, y);
        PNode newNode = new PNode(newNodePosition);
        if (isSourceNode(newNode)) newNode.setType(Type.SOURCE);
        if (isDestinationNode(newNode)) newNode.setType(Type.DESTINATION);
        setNode(newNode, newNodePosition);
      }
    }
  }

  public int getRows() {
    return rowsProperty.get();
  }

  public void setRows(int rows) {
    if (rows <= 0) {
      throw new IllegalArgumentException("number of rows must be strictly positive");
    }
    this.rowsProperty.set(rows);
  }

  @Override
  public IntegerProperty rowsProperty() {
    return rowsProperty;
  }

  public int getColumns() {
    return colsProperty.get();
  }

  public void setColumns(int cols) {
    if (cols <= 0) {
      throw new IllegalArgumentException("number of columns must be strictly positive");
    }
    colsProperty.set(cols);
  }

  @Override
  public IntegerProperty columnsProperty() {
    return colsProperty;
  }

  public IPosition getSourcePosition() {
    return sourcePositionProperty.get();
  }

  @Override
  public boolean isSourceNode(INode node) {
    if (node == null) {
      return false;
    }
    return node.getPosition().equals(getSourcePosition());
  }

  @Override
  public IPosition getDestinationPosition() {
    return destinationPositionProperty.get();
  }

  private void setSourcePosition(IPosition initialSourcePosition) {
    sourcePositionProperty.set(initialSourcePosition);
  }

  @Override
  public void relocateSource(IPosition newSourceLocation) {
    if (newSourceLocation.equals(getSourcePosition())) {
      return;
    }
    getNode(getSourcePosition()).setType(Type.BASIC);
    sourcePositionProperty.set(newSourceLocation);
    getNode(newSourceLocation).setType(Type.SOURCE);
  }

  @Override
  public ObjectProperty<IPosition> sourceNodePositionProperty() {
    return sourcePositionProperty;
  }

  private void setDestinationPosition(IPosition initialDestinationPosition) {
    destinationPositionProperty.set(initialDestinationPosition);
  }

  @Override
  public void relocateDestination(IPosition newDestinationLocation) {
    if (newDestinationLocation.equals(getDestinationPosition())) {
      return;
    }
    getNode(getDestinationPosition()).setType(Type.BASIC);
    destinationPositionProperty.set(newDestinationLocation);
    getNode(newDestinationLocation).setType(Type.DESTINATION);
  }

  @Override
  public ObjectProperty<IPosition> destinationNodePositionProperty() {
    return destinationPositionProperty;
  }

  @Override
  public boolean isDestinationNode(INode node) {
    if (node == null) {
      return false;
    }
    return node.getPosition().equals(getDestinationPosition());
  }

  /** {@inheritDoc} */
  @Override
  public INode getNode(IPosition position) {
    if (GridChecker.checkPosition(position, getRows(), getColumns())) {
      return nodes[position.getX()][position.getY()];
    }
    return PNode.NULL;
  }

  @Override
  public boolean isWalkable(IPosition position) {
    if (GridChecker.checkPosition(position, getRows(), getColumns())) {
      INode node = getNode(position);
      return node.getType() != Type.WALL && getNode(position) != PNode.NULL;
    }
    return false;
  }

  @Override
  public Stream<INode> stream() {
    final List<INode> allNodes = new ArrayList<>();
    for (int x = 0; x < getColumns(); x++) {
      for (int y = 0; y < getRows(); y++) {
        allNodes.add(getNode(Position.of(x, y)));
      }
    }
    return allNodes.stream();
  }

  /** Sets {@code node} at {@code position} */
  private void setNode(PNode node, Position position) {
    GridChecker.checkPosition(position, getRows(), getColumns());
    nodes[position.getX()][position.getY()] = node;
  }

  @Override
  public void clearPath() {
    for (int x = 0; x < getColumns(); x++) {
      for (int y = 0; y < getRows(); y++) {
        INode node = getNode(Position.of(x, y));
        node.clear();
        if (isSourceNode(node)) {
          node.setType(Type.SOURCE);
        } else if (isDestinationNode(node)) {
          node.setType(Type.DESTINATION);
        } else if (not(node.getType().equals(Type.WALL))) {
          node.setType(Type.BASIC);
        }
      }
    }
  }

  @Override
  public void resetGrid() {
    setSourcePosition(Position.of(10, 10));
    setDestinationPosition(Position.of(18, 10));
    for (int x = 0; x < getColumns(); x++) {
      for (int y = 0; y < getRows(); y++) {
        INode node = getNode(Position.of(x, y));
        node.clear();
        if (isSourceNode(node)) {
          node.setType(Type.SOURCE);
        } else if (isDestinationNode(node)) {
          node.setType(Type.DESTINATION);
        } else {
          node.setType(Type.BASIC);
        }
      }
    }
  }

  public static PGrid getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new PGrid(35, 60);
    }
    return INSTANCE;
  }
}
