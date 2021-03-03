package tech.houssemnasri.impl;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

import tech.houssemnasri.api.IGrid;
import tech.houssemnasri.api.IGridPresenter;
import tech.houssemnasri.api.IGridView;
import tech.houssemnasri.api.INode;
import tech.houssemnasri.api.IPosition;
import tech.houssemnasri.api.ITheme;
import tech.houssemnasri.property.ComplexIntegerProperty;
import tech.houssemnasri.property.ComplexObjectProperty;

/** The P in MVP */
public class PGridPresenter implements IGridPresenter {
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
    public INode getNodeModel(IPosition position) {
        return gridModel.getNode(position);
    }

    @Override
    public boolean zoomIn() {
        return false;
    }

    @Override
    public boolean zoomOut() {
        return false;
    }

    @Override
    public void onNodeClicked(IPosition clickedNodePosition) {
        doDrawWall(clickedNodePosition);
    }

    private void doDrawWall(IPosition clickedNodePosition) {
        if (not(clickedNodePosition.equals(PPosition.ERROR))) {
            INode clickedNode = gridModel.getNode(clickedNodePosition);
            if (clickedNode.getType() == INode.Type.BASIC) {
                clickedNode.setType(INode.Type.WALL);
            }
        }
    }

    /** Helper method for better readability */
    private boolean not(boolean bool) {
        return !bool;
    }

    @Override
    public void onNodeDragOver(IPosition draggedOverNodePosition) {
        doDrawWall(draggedOverNodePosition);
    }

    @Override
    public void onNodeHover(IPosition hoverNodePosition) {}

    @Override
    public void onNodeDragged(IPosition draggedNodePosition) {}
}
