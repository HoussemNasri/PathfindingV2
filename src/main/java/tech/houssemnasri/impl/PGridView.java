package tech.houssemnasri.impl;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import tech.houssemnasri.api.IGridPresenter;
import tech.houssemnasri.api.IGridView;
import tech.houssemnasri.api.INodeView;
import tech.houssemnasri.api.IPosition;

public class PGridView implements IGridView {
    private IGridPresenter presenter;
    private GridPane root = new GridPane();

    public PGridView(IGridPresenter presenter) {
        setPresenter(presenter);
    }

    @Override
    public void refresh() {
        int cols = presenter.getColumns();
        int rows = presenter.getRows();

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                IPosition position = PPosition.of(x, y);
                INodeView thisNodeView = new PNodeView(presenter.getNodeModel(position));
                // Add View.
            }
        }
    }

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
