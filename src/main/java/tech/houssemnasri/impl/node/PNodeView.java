package tech.houssemnasri.impl.node;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import tech.houssemnasri.CostEntity;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.node.INodeView;
import tech.houssemnasri.api.node.INode.*;
import tech.houssemnasri.impl.node.painter.BaseNodePainter;

public class PNodeView extends StackPane implements INodeView {
    public static final int INITIAL_NODE_SIZE = 25;
    private INode nodeModel;
    private BaseNodePainter painter;

    private final Text center = new Text();
    private final Text topLeftCorner = new Text();
    private final Text topRightCorner = new Text();
    private final BooleanProperty showCostProperty = new SimpleBooleanProperty(true);

    public PNodeView(INode nodeModel, BaseNodePainter painter) {
        setNodeModel(nodeModel);
        setPainter(painter);
        setPrefWidth(INITIAL_NODE_SIZE);
        setPrefHeight(INITIAL_NODE_SIZE);
        listenForTypeChange();
        listenForCostChange();
        setupCenter();
        setupTopLeftCorner();
        setupTopRightCorner();
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

    private void setupCenter(){
        center.setFont(Font.font(8));
        StackPane.setMargin(center, new Insets(0, 0, 3, 0));
        this.center.setVisible(false);
        StackPane.setAlignment(center, Pos.BOTTOM_CENTER);
        getChildren().add(center);
    }

    private void setupTopLeftCorner(){
        topLeftCorner.setFont(Font.font(5));
        StackPane.setMargin(topLeftCorner, new Insets(3, 0, 0, 3));
        topLeftCorner.setVisible(false);
        StackPane.setAlignment(topLeftCorner, Pos.TOP_LEFT);
        getChildren().add(topLeftCorner);
    }

    private void setupTopRightCorner(){
        topRightCorner.setFont(Font.font(5));
        StackPane.setMargin(topRightCorner, new Insets(3, 3, 0, 0));
        topRightCorner.setVisible(false);
        StackPane.setAlignment(topRightCorner, Pos.TOP_RIGHT);
        getChildren().add(topRightCorner);
    }

    private void listenForCostChange() {
        CostEntity cost = nodeModel.getCostEntity();
        if (cost != null) {
            cost.getCostArguments().addListener(this::doHandleCostChange);
        }
    }

    private void doHandleCostChange(ListChangeListener.Change<? extends Integer> change) {
        while (change.next()) {
            if (change.getAddedSize() == 1) {
                String newValue = String.valueOf(change.getAddedSubList().get(0));
                switch (change.getFrom()){
                    case 0 -> center.setText(newValue);
                    case 1 -> topLeftCorner.setText(newValue);
                    case 2 -> topRightCorner.setText(newValue);
                }
            }
        }
    }

    @Override
    public Text getCenterText() {
        return center;
    }

    @Override
    public Text getTopLeftCornerText() {
        return topLeftCorner;
    }

    @Override
    public Text getTopRightCornerText() {
        return topRightCorner;
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
    public void setShowCostInfo(boolean show) {
        showCostProperty.set(show);
    }

    @Override
    public boolean isShowCostEnabled() {
        return showCostProperty.get();
    }

    @Override
    public BooleanProperty showCostProperty() {
        return this.showCostProperty;
    }
}
