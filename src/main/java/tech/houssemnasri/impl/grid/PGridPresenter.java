package tech.houssemnasri.impl.grid;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import tech.houssemnasri.BooleanExtensions;
import tech.houssemnasri.api.grid.IGrid;
import tech.houssemnasri.api.grid.IGridPresenter;
import tech.houssemnasri.api.grid.IGridView;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.node.IPosition;
import tech.houssemnasri.api.theme.ITheme;
import tech.houssemnasri.impl.node.PPosition;
import tech.houssemnasri.property.ComplexIntegerProperty;
import tech.houssemnasri.property.ComplexObjectProperty;

/** The P in MVP */
public class PGridPresenter implements IGridPresenter, BooleanExtensions {
    private boolean isDraggingSourceNode = false;
    private boolean isDraggingDestinationNode = false;

    private final ObjectProperty<ITheme> themeProperty = new ComplexObjectProperty<>();
    private final IntegerProperty rowsProperty = new ComplexIntegerProperty();
    private final IntegerProperty colsProperty = new ComplexIntegerProperty();

    /** The M in MVP */
    private IGrid gridModel;

    /** The V in MVP */
    private IGridView gridView;

    public PGridPresenter(IGrid gridModel, IGridView gridView, ITheme theme) {
        setGridModel(gridModel);
        setGridView(gridView);
        bindColsPropertyToModel();
        bindRowsPropertyToModel();
        setTheme(theme);
    }

    private void bindColsPropertyToModel() {
        colsProperty.bind(gridModel.columnsProperty());
    }

    private void bindRowsPropertyToModel() {
        rowsProperty.bind(gridModel.rowsProperty());
    }

    private void setGridModel(IGrid gridModel) {
        this.gridModel = gridModel;
    }

    private void setGridView(IGridView gridView) {
        this.gridView = gridView;
        this.gridView.setPresenter(this);
    }

    @Override
    public void setTheme(ITheme newTheme) {
        if (newTheme == null) return;
        themeProperty.set(newTheme);
    }

    @Override
    public ITheme getTheme() {
        return themeProperty.get();
    }

    @Override
    public ObjectProperty<ITheme> themeObjectProperty() {
        return themeProperty;
    }

    @Override
    public int getRows() {
        return rowsProperty.get();
    }

    @Override
    public IntegerProperty rowsProperty() {
        return rowsProperty;
    }

    @Override
    public int getColumns() {
        return colsProperty.get();
    }

    @Override
    public IntegerProperty colsProperty() {
        return colsProperty;
    }

    @Override
    public IPosition getSourcePosition() {
        return gridModel.getSourcePosition();
    }

    @Override
    public ObjectProperty<IPosition> sourcePositionProperty() {
        return gridModel.sourceNodePositionProperty();
    }

    @Override
    public IPosition getDestinationPosition() {
        return gridModel.getDestinationPosition();
    }

    @Override
    public ObjectProperty<IPosition> destinationPositionProperty() {
        return gridModel.destinationNodePositionProperty();
    }

    @Override
    public void setShowCostInfo(boolean showCostInfo) {
        gridView.setShowCostInfo(showCostInfo);
    }

    @Override
    public INode getNodeModel(IPosition position) {
        return gridModel.getNode(position);
    }

    @Override
    public void onNodeClicked(MouseEvent mouseEvent, IPosition clickedNodePosition) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            doDrawWall(clickedNodePosition);
        }
    }

    private void doDrawWall(IPosition clickedNodePosition) {
        if (not(clickedNodePosition.equals(PPosition.ERROR))) {
            INode clickedNode = gridModel.getNode(clickedNodePosition);
            if (clickedNode.getType() == INode.Type.BASIC) {
                clickedNode.setType(INode.Type.WALL);
            }
        }
    }

    @Override
    public void onGridDragged(MouseEvent mouseEvent, IPosition intersection) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            if (isDraggingSourceNode) {
                doRelocateSourceTo(intersection);
            } else if (isDraggingDestinationNode) {
                doRelocateDestinationTo(intersection);
            } else {
                doDrawWall(intersection);
            }
        }
    }

    private void doRelocateDestinationTo(IPosition intersection) {
        if (and(
                not(intersection.equals(PPosition.ERROR)),
                gridModel.isWalkable(intersection),
                not(gridModel.isSourceNode(gridModel.getNode(intersection))))) {
            gridModel.relocateDestination(intersection);
        }
    }

    private void doRelocateSourceTo(IPosition intersection) {
        if (and(
                not(intersection.equals(PPosition.ERROR)),
                gridModel.isWalkable(intersection),
                not(gridModel.isDestinationNode(gridModel.getNode(intersection))))) {
            gridModel.relocateSource(intersection);
        }
    }

    @Override
    public void onNodeHover(IPosition hoverNodePosition) {}

    @Override
    public void onNodePressed(MouseEvent mouseEvent, IPosition intersection) {
        if (and(mouseEvent.isPrimaryButtonDown(), not(intersection.equals(PPosition.ERROR)))) {
            if (gridModel.isSourceNode(gridModel.getNode(intersection))) {
                isDraggingSourceNode = true;
            } else if (gridModel.isDestinationNode(gridModel.getNode(intersection))) {
                isDraggingDestinationNode = true;
            }
        }
    }

    @Override
    public void onMouseRelease(MouseEvent mouseEvent, IPosition releaseNodePosition) {
        isDraggingSourceNode = false;
        isDraggingDestinationNode = false;
    }
}
