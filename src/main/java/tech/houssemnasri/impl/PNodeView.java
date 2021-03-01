package tech.houssemnasri.impl;

import javafx.beans.value.ObservableValue;
import javafx.scene.layout.StackPane;

import tech.houssemnasri.api.INode;
import tech.houssemnasri.api.INodeView;
import tech.houssemnasri.api.INode.*;

public class PNodeView extends StackPane implements INodeView {
    private INode nodeModel;

    public PNodeView(INode nodeModel) {
        setNodeModel(nodeModel);
        listenForTypeChange();
        setStyle("-fx-background-color: white");
        setPrefWidth(50);
        setPrefHeight(50);
    }

    private void setNodeModel(INode nodeModel) {
        this.nodeModel = nodeModel;
    }

    @Override
    public INode getNodeModel() {
        return nodeModel;
    }

    private void paintView(ObservableValue<? extends Type> observable, Type oldValue, Type nodeType) {
        switch (nodeType){
            case BASIC -> paintBasic();
            case OPEN -> paintOpen();
            case CLOSED -> paintClosed();
            case WALL -> paintWall();
            case PATH -> paintPath();
            case SOURCE -> paintSource();
            case DESTINATION -> paintDestination();
        }
    }

    private void paintDestination() {
    }

    private void paintSource() {
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
