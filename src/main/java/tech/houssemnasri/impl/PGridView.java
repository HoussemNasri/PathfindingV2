package tech.houssemnasri.impl;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import tech.houssemnasri.api.IGrid;
import tech.houssemnasri.api.IGridPresenter;
import tech.houssemnasri.api.IGridView;
import tech.houssemnasri.api.INodeView;
import tech.houssemnasri.api.IPosition;
import tech.houssemnasri.api.ITheme;
import tech.houssemnasri.property.ComplexIntegerProperty;
import tech.houssemnasri.property.ComplexObjectProperty;

public class PGridView implements IGridView {
    private IGridPresenter presenter;
    private final ObjectProperty<ITheme> themeProperty = new ComplexObjectProperty<>();
    private GridPane root;
    private INodeView.NodeSize nodeSize;
    private IPosition sourceNodePosition;
    private IPosition destinationNodePosition;
    private IGrid gridModel;

    private final IntegerProperty colsProperty = new ComplexIntegerProperty(10);
    private final IntegerProperty rowsProperty = new ComplexIntegerProperty(10);

    public PGridView(IGrid gridModel, PTheme theme) {
        setGridModel(gridModel);
        setTheme(theme);
        bindColsProperty();
        bindRowsProperty();
    }

    private void bindColsProperty() {
        colsProperty.bind(gridModel.columnsProperty());
    }

    private void bindRowsProperty() {
        rowsProperty.bind(gridModel.rowsProperty());
    }

    @Override
    public ITheme getTheme() {
        return themeProperty.get();
    }

    @Override
    public void setTheme(ITheme newTheme) {
        themeProperty.set(newTheme);
    }

    @Override
    public void update() {}

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void showCostInfo(boolean show) {}

    @Override
    public void setPresenter(IGridPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setGridModel(IGrid gridModel) {
        this.gridModel = gridModel;
    }

    @Override
    public void setNodeSize(INodeView.NodeSize nodeSize) {
        this.nodeSize = nodeSize;
    }

    public void setSourceNodePosition(IPosition sourceNodePosition) {
        this.sourceNodePosition = sourceNodePosition;
    }

    public void setDestinationNodePosition(IPosition destinationNodePosition) {
        this.destinationNodePosition = destinationNodePosition;
    }

    private void setCols(Integer cols) {
        colsProperty.set(cols);
    }

    @Override
    public int getColumns() {
        return colsProperty.get();
    }

    private void setRows(Integer rows) {
        rowsProperty.set(rows);
    }

    @Override
    public int getRows() {
        return rowsProperty.get();
    }
}
