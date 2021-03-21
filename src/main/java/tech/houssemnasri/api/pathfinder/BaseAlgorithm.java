package tech.houssemnasri.api.pathfinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import tech.houssemnasri.BooleanExtensions;
import tech.houssemnasri.api.command.ICommand;
import tech.houssemnasri.api.grid.IGrid;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.node.IPosition;
import tech.houssemnasri.impl.command.CommandRecord;
import tech.houssemnasri.impl.command.CommandHistory;
import tech.houssemnasri.impl.command.TracePathCommand;
import tech.houssemnasri.impl.node.PPosition;
import tech.houssemnasri.util.GridChecker;

/**
 * This class {@code BaseAlgorithm} should provide an api for the {@code BaseAlgorithmPlayer} to
 * explore the grid for {@code destinationNode} using {@code this} algorithm logic, also for going
 * back to the previous algorithm state.
 */
public abstract class BaseAlgorithm implements BooleanExtensions {
  protected final IGrid grid;
  protected final boolean isDiagonalAllowed;
  private final CommandHistory algorithmHistory = new CommandHistory();
  protected INode currentNode;

  public BaseAlgorithm(IGrid grid, boolean isDiagonalAllowed) {
    this.grid = grid;
    this.isDiagonalAllowed = isDiagonalAllowed;
    initialize();
  }

  public BaseAlgorithm(IGrid grid) {
    this(grid, false);
  }

  protected abstract void initialize();

  /**
   * move forward and explore the grid looking for the path from {@code source} to {@code
   * destination}
   */
  public abstract void forward();
  /** go back to previous grid state and remove recent algorithm changes. */
  public final void back() {
    CommandRecord stepRecord = algorithmHistory.pop();
    while (not(stepRecord.isEmpty())) {
      stepRecord.pop().undo();
    }
  }

  public void setCurrentNode(INode node) {
    this.currentNode = node;
  }
  /**
   * The current selected node by the algorithm .e.g. A* choose a node then explore all of it's
   * neighbours, the currentNode would be the chosen node.
   */
  public INode getCurrentNode() {
    return currentNode;
  }

  /** @return True if {@code currentNode} is the {@code destinationNode}, False otherwise. */
  public boolean isPathFound() {
    return grid.isDestinationNode(getCurrentNode());
  }

  public IGrid getGrid() {
    return grid;
  }

  /**
   * Returns whether the algorithm is allowed to explore diagonals.
   *
   * @return {@code True} if exploring diagonals is allowed, {@code False} otherwise
   */
  public boolean isDiagonalAllowed() {
    return isDiagonalAllowed;
  }

  /** Returns list of unique {@code INode} which this algorithm can explore. */
  public abstract Set<INode> getOpenSet();

  /** Returns list of unique {@code INode} which this algorithm already explored. */
  public abstract Set<INode> getClosedSet();

  protected final boolean isPositionValid(IPosition position) {
    return GridChecker.checkPosition(position, grid.getRows(), grid.getColumns());
  }

  protected final boolean isWalkable(IPosition position) {
    return grid.isWalkable(position);
  }

  protected final boolean isNodeClosed(INode node) {
    return getClosedSet().contains(node);
  }

  protected final boolean isNodeOpen(INode node) {
    return getOpenSet().contains(node);
  }

  public void doTraceBackPath() {
    saveRecord(new TracePathCommand(this, getCurrentNode()).executeAndReturn());
  }

  protected boolean isNodeOnDiagonalOfCurrent(INode node) {
    int dx = getCurrentNode().getPosition().getX() - node.getPosition().getX();
    int dy = getCurrentNode().getPosition().getY() - node.getPosition().getY();
    return Math.abs(dx) - Math.abs(dy) == 0;
  }

  protected final List<INode> getCurrentNodeNeighbors() {
    List<INode> result = new ArrayList<>();
    int currX = getCurrentNode().getPosition().getX();
    int currY = getCurrentNode().getPosition().getY();
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        int neiX = currX + i;
        int neiY = currY + j;
        IPosition neiPosition = PPosition.of(neiX, neiY);
        if (not(isPositionValid(neiPosition))) {
          continue;
        } else if (not(isWalkable(neiPosition))) {
          continue;
        } else if (PPosition.of(currX, currY).equals(neiPosition)) {
          continue;
        } else if (not(isDiagonalAllowed())) {
          if (Math.abs(i) - Math.abs(j) == 0) {
            continue;
          }
        }
        result.add(grid.getNode(neiPosition));
      }
    }
    return result;
  }

  protected void saveRecord(CommandRecord algorithmStep) {
    algorithmHistory.push(algorithmStep);
  }

  protected void saveRecord(ICommand command) {
    saveRecord(new CommandRecord(command));
  }
}
