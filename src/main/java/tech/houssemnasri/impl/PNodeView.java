package tech.houssemnasri.impl;

import javafx.beans.value.ObservableValue;
import javafx.scene.layout.StackPane;

import tech.houssemnasri.api.INode;
import tech.houssemnasri.api.INodeView;
import tech.houssemnasri.api.INode.*;
import tech.houssemnasri.api.ITheme;

public class PNodeView extends StackPane implements INodeView {
    private INode nodeModel;
    private ITheme theme;

    public PNodeView(INode nodeModel) {
        setNodeModel(nodeModel);
        listenForTypeChange();
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

    @Override
    public void setTheme(ITheme newTheme) {
        this.theme = newTheme;
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
        setStyle("-fx-background-color: #ff8a30");
    }

    private void paintSource() {
        setStyle("-fx-background-color: #8d39f8");
    }

    private void paintBasic(){
        setStyle("-fx-background-color: white");
    }
    private void paintOpen(){
        setStyle("-fx-background-color: #2cd92c");
    }

    private void paintClosed(){
        setStyle("-fx-background-color: #ff484f");
    }
    private void paintWall(){
        setStyle("-fx-background-color: #242424");
    }

    private void paintPath() {
        setStyle("-fx-background-color: #1194e2");
    }

    private void listenForTypeChange(){
        nodeModel.nodeTypeProperty().addListener(this::paintView);
    }

    @Override
    public void showCostInfo(boolean show) {}
}
