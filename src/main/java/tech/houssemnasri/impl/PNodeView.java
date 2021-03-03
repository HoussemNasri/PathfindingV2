package tech.houssemnasri.impl;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import tech.houssemnasri.api.INode;
import tech.houssemnasri.api.INodeView;
import tech.houssemnasri.api.INode.*;
import tech.houssemnasri.api.ITheme;
import tech.houssemnasri.property.ComplexObjectProperty;

public class PNodeView extends StackPane implements INodeView {
    private INode nodeModel;
    private ITheme theme;
    private final ObjectProperty<ITheme> themeObjectProperty = new ComplexObjectProperty<>();

    public PNodeView(INode nodeModel, ITheme theme) {
        setNodeModel(nodeModel);
        listenForTypeChange();
        setPrefWidth(50);
        setPrefHeight(50);
        setTheme(theme);
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

    @Override
    public ObjectProperty<ITheme> themeProperty() {
        return themeObjectProperty;
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
        setBackgroundColor(theme.getOpenNodeColor());
    }

    private void paintPath() {
        setStyle("-fx-background-color: #1194e2");
    }

    private void listenForTypeChange(){
        nodeModel.nodeTypeProperty().addListener(this::paintView);
    }

    private void setBackgroundColor(Color color){
        Background coloredBackground = new Background(new BackgroundFill(
                color, null,null
        ));
        setBackground(coloredBackground);
    }

    @Override
    public void showCostInfo(boolean show) {}
}
