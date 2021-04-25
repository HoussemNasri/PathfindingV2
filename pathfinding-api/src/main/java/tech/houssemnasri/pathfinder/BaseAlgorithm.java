package tech.houssemnasri.pathfinder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tech.houssemnasri.BooleanExtensions;
import tech.houssemnasri.node.Position;
import tech.houssemnasri.command.TracePathCommand;
import tech.houssemnasri.grid.IGrid;
import tech.houssemnasri.node.INode;
import tech.houssemnasri.util.GridChecker;

/**
 * This class {@code BaseAlgorithm} should provide an api for the {@code Visualizer} to explore the
 * grid for {@code destinationNode} using {@code this} algorithm logic, also for going back to the
 * previous algorithm state.
 */
public abstract class BaseAlgorithm implements BooleanExtensions {
  protected static final int HORIZ_VERT_DISTANCE = 10;
  protected static final int DIAGONAL_DISTANCE = 14;
  private final Set<INode> visitedNodes = new HashSet<>();
  private final Set<INode> unvisitedNodes = new HashSet<>();

  protected final IGrid grid;
  protected final boolean isDiagonalAllowed;
  private final AlgorithmHistory history = new AlgorithmHistory();
  protected INode currentNode;

  public BaseAlgorithm(IGrid grid, boolean isDiagonalAllowed) {
    this.grid = grid;
    this.isDiagonalAllowed = isDiagonalAllowed;
  }

  public BaseAlgorithm(IGrid grid) {
    this(grid, false);
  }

  /**
   * move forward and explore the grid looking for the path from {@code source} to {@code
   * destination}
   */
  protected abstract AlgorithmStep advance();

  /** Step in the algorithm and record the step */
  public final void forward() {
    AlgorithmStep step = advance();
    if (not(step.isEmpty())) {
      addToHistory(step);
    }
  }

  /** Undo the most recent algorithm step. */
  public final void back() {
    if (not(history.isEmpty())) {
      history.pop().cancel();
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

  /** @return True if the last step is the final step. */
  public boolean isPathFound() {
    if (history.isEmpty()) {
      return false;
    }
    return history.peek().isFinalStep();
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

  /** Returns list of all unique {@code INode} which the algorithm didn't visit. */
  public Set<INode> getUnvisitedNodes() {
    return unvisitedNodes;
  }

  /** Returns list of unique {@code INode} which the algorithm already visited. */
  public Set<INode> getVisitedNodes() {
    return visitedNodes;
  }

  protected final boolean isPositionValid(Position position) {
    return GridChecker.checkPosition(
        position.getX(), position.getY(), grid.getRows(), grid.getColumns());
  }

  protected final boolean isWalkable(Position position) {
    return grid.isWalkable(position);
  }

  protected final boolean isVisited(INode node) {
    return getVisitedNodes().contains(node);
  }

  protected final boolean isNodeOpen(INode node) {
    return getUnvisitedNodes().contains(node);
  }

  public void tracePath() {
    // Don't trace path twice.
    if (history.peek().peek() instanceof TracePathCommand) {
      return;
    }
    INode destination = grid.getNode(grid.getDestinationPosition());
    AlgorithmStep tracePathStep = new AlgorithmStep();
    tracePathStep.markAsFinal();
    tracePathStep.pushAndExecute(new TracePathCommand(destination));
    addToHistory(tracePathStep);
  }

  /**
   * Marks {@code step} as the final step and returns it.
   *
   * @return the final step
   */
  protected AlgorithmStep finalize(AlgorithmStep step) {
    step.markAsFinal();
    return step;
  }

  protected boolean isOnDiagonal(INode node) {
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
        Position nePosition = Position.of(neiX, neiY);
        if (not(isPositionValid(nePosition))) {
          continue;
        } else if (not(isWalkable(nePosition))) {
          continue;
        } else if (Position.of(currX, currY).equals(nePosition)) {
          continue;
        } else if (not(isDiagonalAllowed())) {
          if (Math.abs(i) - Math.abs(j) == 0) {
            continue;
          }
        }
        result.add(grid.getNode(nePosition));
      }
    }
    return result;
  }

  protected void addToHistory(AlgorithmStep algorithmStep) {
    history.push(algorithmStep);
  }

  public void reset() {
    getVisitedNodes().clear();
    getUnvisitedNodes().clear();
    history.clear();
    getGrid().clearPath();
    setCurrentNode(null);
  }
}
