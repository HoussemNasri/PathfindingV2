package tech.houssemnasri.api.algorithms;

import tech.houssemnasri.api.grid.IGrid;
import tech.houssemnasri.api.node.INode;

/**
 * This class {@code BaseAlgorithm} should provide an api for the {@code BaseAlgorithmPlayer} to
 * explore the grid for {@code destinationNode} using {@code this} algorithm logic, also for going
 * back to the previous algorithm state.
 */
public abstract class BaseAlgorithm {
    protected final IGrid grid;
    protected final boolean isDiagonalAllowed;

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
    public abstract void forward();
    /** go back to previous grid state and remove recent algorithm changes. */
    public abstract void back();
    /**
     * The current selected node by the algorithm .e.g. A* choose a node then explore all of it's
     * neighbours, the currentNode would be the chosen node.
     */
    public abstract INode getCurrentNode();

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
}
