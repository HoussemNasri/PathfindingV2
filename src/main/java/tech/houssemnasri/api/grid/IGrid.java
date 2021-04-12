package tech.houssemnasri.api.grid;

import java.util.stream.Stream;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.node.IPosition;
import tech.houssemnasri.impl.node.PNode;

/**
 * An interface for the grid component allowing us to create multiple grid variations with the same
 * behaviour but different implementation.
 */
public interface IGrid {
  int DEFAULT_SOURCE_X = 10;
  int DEFAULT_SOURCE_Y = 10;
  int DEFAULT_DESTINATION_X = 18;
  int DEFAULT_DESTINATION_Y = 10;

  /** Returns the number of rows */
  int getRows();
  /** Specify the number of rows for the grid. */
  void setRows(int rows);

  IntegerProperty rowsProperty();

  /** Returns the number of columns */
  int getColumns();

  /** Specify the number of columns for the grid. */
  void setColumns(int cols);

  IntegerProperty columnsProperty();

  /** Returns source node position in grid. */
  IPosition getSourcePosition();

  /**
   * change the position of the source node to {@code newSourceLocation} and perform the transition
   * of states for the previous source node and the new source node.
   */
  void relocateSource(IPosition newSourceLocation);

  ObjectProperty<IPosition> sourceNodePositionProperty();

  /** Returns destination node position in grid. */
  IPosition getDestinationPosition();

  /**
   * change the position of the destination node to {@code newDestinationLocation} and perform the
   * transition of states for the previous destination node and the new destination node.
   */
  void relocateDestination(IPosition newDestinationLocation);

  ObjectProperty<IPosition> destinationNodePositionProperty();

  /**
   * Returns whether {@code node} is the source node or not.
   *
   * @param node the potential source node
   * @return True if {@code node} is the source node, False otherwise
   */
  boolean isSourceNode(INode node);

  /**
   * Returns whether {@code node} is the destination node or not.
   *
   * @param node the potential destination node
   * @return True if {@code node} is the destination node, False otherwise
   */
  boolean isDestinationNode(INode node);

  /**
   * Returns the node at the specified position in this grid.
   *
   * @param position position of the node to return
   * @return the node at the specified position in this grid
   */
  INode getNode(IPosition position);

  boolean isWalkable(IPosition position);

  Stream<INode> stream();

  /**
   * Clear all {@code OPEN}, {@code CLOSED} and {@code PATH} nodes by resetting them to {@code
   * BASIC}, also leaving all other nodes untouched.
   */
  void clearPath();

  /**
   * Clear all nodes including {@code WALL} nodes, also relocate source and destination nodes to
   * their default location
   */
  void resetGrid();
}
