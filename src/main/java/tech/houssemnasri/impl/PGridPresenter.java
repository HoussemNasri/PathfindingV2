package tech.houssemnasri.impl;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.input.ScrollEvent;

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
    /** The minimum scale zoom out can reach. */
    private static final double MIN_SCALE = 1;
    /** The maximum scale zoom in can reach. */
    private static final double MAX_SCALE = 10;
    /** The amount of which the scale increase or decrease each zoomIn or zoomOut */
    private static final double SCALE_DELTA = 1.2;

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
        double scale = gridView.getScale() * SCALE_DELTA;
        gridView.setScale(clamp(scale, MIN_SCALE, MAX_SCALE), true);
        return false;
    }

    @Override
    public boolean zoomOut() {
        double scale = gridView.getScale() / SCALE_DELTA;
        gridView.setScale(clamp(scale, MIN_SCALE, MAX_SCALE), true);
        return false;
    }

    /**
     * Clamps the given value between the given minimum float and maximum float values. Returns the
     * given value if it is within the min and max range.
     *
     * <p>Returns the min value if the given float value is less than the min. Returns the max value
     * if the given value is greater than the max value. Use Clamp to restrict a value to a range
     * that is defined by the min and max values.
     *
     * @param value floating point value to restrict inside the range defined by the min and max
     *     values.
     * @param min minimum floating point value to compare against.
     * @param max maximum floating point value to compare against.
     * @return value within min and max range
     */
    private double clamp(double value, double min, double max) {
        if (Double.compare(value, min) < 0) return min;

        if (Double.compare(value, max) > 0) return max;

        return value;
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

    @Override
    public void onScroll(ScrollEvent scrollEvent) {
        if (scrollEvent.getDeltaY() < 0) {
            zoomOut();
        } else {
            zoomIn();
        }
    }
}
