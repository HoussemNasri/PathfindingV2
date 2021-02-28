package tech.houssemnasri.impl;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import tech.houssemnasri.api.IGridPresenter;
import tech.houssemnasri.api.IGridView;
import tech.houssemnasri.api.INodeView;

public class PGridView implements IGridView {
    private IGridPresenter presenter;
    private GridPane root;

    public PGridView(IGridPresenter presenter) {
        setPresenter(presenter);
    }

    @Override
    public void refresh() {}

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void showCostInfo(boolean show) {}

    @Override
    public void setPresenter(IGridPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setNodeSize(INodeView.NodeSize newNodeSize) {
        // Update the UI with the new node size
    }

    public IGridPresenter getPresenter() {
        return presenter;
    }
}
