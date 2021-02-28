package tech.houssemnasri.api;

import javafx.scene.layout.Pane;

public interface IGridView {
    /**
     * Returns the currently used theme.
     */
    ITheme getTheme();

    /**
     * Setting and updating the current theme.
     */
    void setTheme(ITheme newTheme);

    /**
     * Render the current state of the grid model on the screen.
     */
    void update();

    Pane getView();

    /**
     * Showing cost info on the views when using an algorithm that uses costs.
     *
     * @param show If true cost information will be visible.
     */
    void showCostInfo(boolean show);

    /**
     * Enforcing the MVP architecture pattern by having a reference to the view presenter.
     */
    void setPresenter(IGridPresenter presenter);

    void setGridModel(IGrid gridModel);

    /**
     * Set the size of all nodes on the grid to {@code nodeSize} all re-render grid.
     */
    void setNodeSize(INodeView.NodeSize nodeSize);


    int getColumns();

    int getRows();
}
