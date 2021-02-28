package tech.houssemnasri.impl;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

import tech.houssemnasri.api.IGridPresenter;
import tech.houssemnasri.api.INode;
import tech.houssemnasri.api.INodeView;
import tech.houssemnasri.api.IPosition;
import tech.houssemnasri.api.ITheme;

public class PGridPresenter implements IGridPresenter {
    @Override
    public void setTheme(ITheme newTheme) {

    }

    @Override
    public ITheme getTheme() {
        return null;
    }

    @Override
    public ObjectProperty<ITheme> themeObjectProperty() {
        return null;
    }

    @Override
    public void setNodeSize(INodeView.NodeSize nodeSize) {

    }

    @Override
    public INodeView.NodeSize getNodeSize() {
        return null;
    }

    @Override
    public ObjectProperty<INodeView.NodeSize> nodeSizeObjectProperty() {
        return null;
    }

    @Override
    public int getRows() {
        return 0;
    }

    @Override
    public IntegerProperty rowsProperty() {
        return null;
    }

    @Override
    public int getColumns() {
        return 0;
    }

    @Override
    public IntegerProperty colsProperty() {
        return null;
    }

    @Override
    public INode getNodeModel(IPosition position) {
        return null;
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

    }

    @Override
    public void onNodeDragOver(IPosition draggedOverNodePosition) {

    }

    @Override
    public void onNodeHover(IPosition hoverNodePosition) {

    }

    @Override
    public void onNodeDragged(IPosition draggedNodePosition) {

    }
}
