package tech.houssemnasri.impl;

import javafx.scene.layout.Pane;

import tech.houssemnasri.api.IGrid;
import tech.houssemnasri.api.IGridPresenter;
import tech.houssemnasri.api.IGridView;
import tech.houssemnasri.api.INode;
import tech.houssemnasri.api.INodeView;
import tech.houssemnasri.api.IPosition;
import tech.houssemnasri.api.ITheme;

public class PGridView implements IGridView {
    private IGridPresenter presenter;
    private ITheme theme;
    private Pane view;
    private INodeView.NodeSize nodeSize;
    private IPosition sourceNodePosition;
    private IPosition destinationNodePosition;
    private IGrid gridModel;

    private int cols;
    private int rows;

    public PGridView(IGrid gridModel) {
        setGridModel(gridModel);
    }

    @Override
    public ITheme getTheme() {
        return theme;
    }

    @Override
    public void setTheme(ITheme newTheme) {}

    @Override
    public void update() {}

    @Override
    public Pane getView() {
        return view;
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

    @Override
    public int getColumns() {
        return cols;
    }

    @Override
    public int getRows() {
        return rows;
    }
}
