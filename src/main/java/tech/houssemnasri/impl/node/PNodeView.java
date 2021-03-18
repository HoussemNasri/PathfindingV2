package tech.houssemnasri.impl.node;

import javafx.beans.value.ObservableValue;
import javafx.scene.layout.StackPane;

import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.node.INodeView;
import tech.houssemnasri.api.node.INode.*;
import tech.houssemnasri.impl.node.painter.BaseNodePainter;

public class PNodeView extends StackPane implements INodeView {
    public static final int INITIAL_NODE_SIZE = 25;
    private INode nodeModel;
    private BaseNodePainter painter;

    public PNodeView(INode nodeModel, BaseNodePainter painter) {
        setNodeModel(nodeModel);
        setPainter(painter);
        setPrefWidth(INITIAL_NODE_SIZE);
        setPrefHeight(INITIAL_NODE_SIZE);
        listenForTypeChange();
    }

    public PNodeView(INode nodeModel) {
        this(nodeModel, null);
    }

    private void setNodeModel(INode nodeModel) {
        this.nodeModel = nodeModel;
    }

    @Override
    public void setPainter(BaseNodePainter painter) {
        if (painter != null) {
            this.painter = painter;
            painter.themeProperty().addListener(e -> repaint());
            repaint();
        }
    }

    @Override
    public INode getNodeModel() {
        return nodeModel;
    }

    @Override
    public BaseNodePainter getPainter() {
        return painter;
    }

    @Override
    public void repaint() {
        doPaint(null, null, nodeModel.getType());
    }

    private void doPaint(ObservableValue<? extends Type> observable, Type oldValue, Type nodeType) {
        if (painter != null) {
            painter.paint(nodeType);
        }
    }

    private void listenForTypeChange() {
        nodeModel.nodeTypeProperty().addListener(this::doPaint);
    }

    @Override
    public void setShowCostInfo(boolean show) {}
}
