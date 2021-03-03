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
    private final ObjectProperty<ITheme> themeObjectProperty = new ComplexObjectProperty<>();

    public PNodeView(INode nodeModel, ITheme theme) {
        setNodeModel(nodeModel);
        setPrefWidth(50);
        setPrefHeight(50);
        setTheme(theme);
        listenForThemeChange();
        listenForTypeChange();
    }

    public PNodeView(INode nodeModel){
        this(nodeModel, null);
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
        if(newTheme != null)
            themeObjectProperty.set(newTheme);
    }

    @Override
    public ITheme getTheme() {
        return themeObjectProperty.get();
    }

    @Override
    public ObjectProperty<ITheme> themeProperty() {
        return themeObjectProperty;
    }

    private void paintView(ObservableValue<? extends Type> observable, Type oldValue, Type nodeType) {
        //At startup we might not have the theme ready so we need to wait for
        // the presenter to send us the theme, and execute the theming code at {code changeTheme()}
        if(getTheme() == null) return;
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
        setBackgroundColor(getTheme().getDestinationNodeColor());
    }

    private void paintSource() {
        setBackgroundColor(getTheme().getSourceNodeColor());
    }

    private void paintBasic(){
        setBackgroundColor(getTheme().getBasicNodeColor());
    }
    private void paintOpen(){
        setBackgroundColor(getTheme().getOpenNodeColor());
    }

    private void paintClosed(){
        setBackgroundColor(getTheme().getClosedNodeColor());
    }
    private void paintWall(){
        setBackgroundColor(getTheme().getWallNodeColor());
    }

    private void paintPath() {
        setBackgroundColor(getTheme().getPathNodeColor());
    }

    private void listenForTypeChange(){
        nodeModel.nodeTypeProperty().addListener(this::paintView);
    }

    private void changeTheme(ObservableValue<? extends ITheme> observable, ITheme oldValue, ITheme nodeType){
        paintView(null, null , nodeModel.getType());
    }

    private void listenForThemeChange(){
        themeObjectProperty.addListener(this::changeTheme);
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
