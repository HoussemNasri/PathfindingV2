package tech.houssemnasri.impl.node;

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
    private Text centerText;

    public PNodeView(INode nodeModel, BaseNodePainter painter) {
        setNodeModel(nodeModel);
        setPainter(painter);
        setPrefWidth(INITIAL_NODE_SIZE);
        setPrefHeight(INITIAL_NODE_SIZE);
        listenForTypeChange();
        listenForCostChange();
        setCenterText();
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

    private void setCenterText() {
        this.centerText = new Text();
        this.centerText.setFont(Font.font(8));
        StackPane.setMargin(centerText, new Insets(0, 0, 4, 0));
        this.centerText.setVisible(false);
        StackPane.setAlignment(centerText, Pos.BOTTOM_CENTER);
        getChildren().add(centerText);
    }

    private void listenForCostChange() {
        CostEntity cost = nodeModel.getCostEntity();
        if (cost != null) {
            cost.getCostArguments().addListener(this::doHandleCostChange);
        }
    }

    private void doHandleCostChange(ListChangeListener.Change<? extends Integer> change) {
        while (change.next()) {
            System.out.printf(
                    "[%d : %d] -> %d%n", change.getFrom(), change.getTo(), change.getAddedSize());
            if (change.getAddedSize() == 1) {
                if (change.getFrom() == 0) {
                    centerText.setText(String.valueOf(change.getAddedSubList().get(0)));
                }
            }
        }
    }

    @Override
    public Text getCenterText() {
        return centerText;
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
