package tech.houssemnasri.impl;

import javafx.beans.value.ObservableValue;
import javafx.scene.layout.StackPane;

import tech.houssemnasri.api.INode;
import tech.houssemnasri.api.INodeView;
import tech.houssemnasri.api.INode.*;

public class PNodeView extends StackPane implements INodeView {
    private INode nodeModel;
    private NodeSize viewSize;

    public PNodeView(INode nodeModel, NodeSize viewSize) {
        setNodeModel(nodeModel);
        setViewSize(viewSize);
        listenForTypeChange();
    }

    public PNodeView(PNode nodeModel) {
        this(nodeModel, NodeSize.MEDIUM);
    }

    private void setNodeModel(INode nodeModel) {
        this.nodeModel = nodeModel;
    }

    @Override
    public INode getNodeModel() {
        return nodeModel;
    }

    @Override
    public void setViewSize(NodeSize newViewSize) {
        this.viewSize = newViewSize;
    }

    @Override
    public NodeSize getViewSize() {
        return viewSize;
    }

    private void paintView(ObservableValue<? extends Type> observable, Type oldValue, Type nodeType) {
        switch (nodeType){
            case BASIC -> paintBasic();
            case OPEN -> paintOpen();
            case CLOSED -> paintClosed();
            case WALL -> paintWall();
            case PATH -> paintPath();
        }
    }



    private void paintBasic(){

    }
    private void paintOpen(){

    }

    private void paintClosed(){

    }
    private void paintWall(){

    }

    private void paintPath() {
    }

    private void listenForTypeChange(){
        nodeModel.nodeTypeProperty().addListener(this::paintView);
    }

    @Override
    public void showCostInfo(boolean show) {}
}
