package tech.houssemnasri.impl.node;

import javafx.beans.value.ObservableValue;
import javafx.scene.layout.StackPane;

import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.node.INodeView;
import tech.houssemnasri.api.node.INode.*;

public class PNodeView extends StackPane implements INodeView {
    public static final int INITIAL_NODE_SIZE = 25;
    private INode nodeModel;
    private NodePainter painter;

    public PNodeView(INode nodeModel) {
        setNodeModel(nodeModel);
        setPrefWidth(INITIAL_NODE_SIZE);
        setPrefHeight(INITIAL_NODE_SIZE);
        listenForTypeChange();
    }

    private void setNodeModel(INode nodeModel) {
        this.nodeModel = nodeModel;
    }

    @Override
    public INode getNodeModel() {
        return nodeModel;
    }

    @Override
    public void setPainter(NodePainter painter) {
        this.painter = painter;
        doPaint(null, null, nodeModel.getType());
    }

    @Override
    public NodePainter getPainter() {
        return painter;
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
    public void showCostInfo(boolean show) {}
}
