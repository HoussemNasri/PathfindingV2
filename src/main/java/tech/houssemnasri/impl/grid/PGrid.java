package tech.houssemnasri.impl.grid;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

import tech.houssemnasri.util.GridChecker;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.grid.IGrid;
import tech.houssemnasri.api.node.IPosition;
import tech.houssemnasri.impl.node.PNode;
import tech.houssemnasri.impl.node.PPosition;
import tech.houssemnasri.property.ComplexIntegerProperty;
import tech.houssemnasri.property.ComplexObjectProperty;

/**
 * The {@code PGrid} class represent the grid state, it keeps all state variables in one place,
 * PGrid is a singleton object because with the current app state there is no reason to have more
 * than one.
 */
public final class PGrid implements IGrid, Serializable {
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
        relocateSource(sourcePosition);
        relocateDestination(destinationPosition);
        nodes = new PNode[getColumns()][getRows()];
        createNodes();
    }

    private PGrid(int rows, int cols) {
        this(rows, cols, PPosition.of(0, 0), PPosition.of(cols - 1, rows - 1));
    }

    /** create and initialize the nodes based on the current state */
    private void createNodes() {
        for (int x = 0; x < getColumns(); x++) {
            for (int y = 0; y < getRows(); y++) {
                PPosition newNodePosition = PPosition.of(x, y);
                PNode newNode = new PNode(newNodePosition);
                if (isSourceNode(newNode)) newNode.setType(INode.Type.SOURCE);
                if (isDestinationNode(newNode)) newNode.setType(INode.Type.DESTINATION);
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
        if(node == null) {
            return false;
        }
        return node.getPosition().equals(getSourcePosition());
    }

    @Override
    public IPosition getDestinationPosition() {
        return destinationPositionProperty.get();
    }

    @Override
    public void relocateSource(IPosition newSourceLocation) {
        sourcePositionProperty.set(newSourceLocation);
    }

    @Override
    public ObjectProperty<IPosition> sourceNodePositionProperty() {
        return sourcePositionProperty;
    }

    @Override
    public void relocateDestination(IPosition newDestinationLocation) {
        destinationPositionProperty.set(newDestinationLocation);
    }

    @Override
    public ObjectProperty<IPosition> destinationNodePositionProperty() {
        return destinationPositionProperty;
    }

    @Override
    public boolean isDestinationNode(INode node) {
        if(node == null) {
            return false;
        }
        return node.getPosition().equals(getDestinationPosition());
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public PNode getNode(IPosition position) {
        if(GridChecker.checkPosition(position, getRows(), getColumns())){
            return nodes[position.getX()][position.getY()];
        }
        return null;
    }

    /** Sets {@code node} at {@code position} */
    private void setNode(PNode node, PPosition position) {
        GridChecker.checkPosition(position, getRows(), getColumns());
        nodes[position.getX()][position.getY()] = node;
    }

    @Override
    public void clearPath() {
        for (int x = 0; x < getColumns(); x++) {
            for (int y = 0; y < getRows(); y++) {
                PNode node = getNode(PPosition.of(x, y));
                INode.Type nodeType = node.getType();
                switch (nodeType){
                    case PATH,OPEN,CLOSED -> node.clear();
                }
            }
        }
    }

    @Override
    public void resetGrid() {
        relocateSource(PPosition.of(0, 0));
        relocateDestination(PPosition.of(getColumns() - 1, getRows() - 1));
        for (int x = 0; x < getColumns(); x++) {
            for (int y = 0; y < getRows(); y++) {
                PNode node = getNode(PPosition.of(x, y));
                if (!(isSourceNode(node) || isDestinationNode(node))) {
                    node.clear();
                }
            }
        }
    }

    public static PGrid getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PGrid(28, 55);
        }
        return INSTANCE;
    }
}
