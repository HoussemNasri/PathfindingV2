package tech.houssemnasri.api;

import javafx.scene.layout.Pane;

public interface IGridPresenter {
    /**
     * Returns the grid model.
     */
    IGrid getGrid();

    /**
     * The Presenter interact with the Views via View interface
     */
    IGridView getGridView();

    /**
     * Zoom in, and increase node size.
     *
     * @return True if the zoom happened and we didn't reach our maximum zoom level.
     */
    boolean zoomIn();

    /**
     * Zoom out, and decrease node size.
     *
     * @return True if the zoom happened and we didn't reach our maximum zooming level.
     */
    boolean zoomOut();

    /**
     * Returns the current zooming level.
     */
    int getZoomLevel();

    /**
     * Returns whether the cost information is visible to the user or not, to show cost the user need to enable it plus the nodes has to be big enough to fit the cost view.
     *
     * @return True if show cost is enabled and the nodes are big enough to fit cost view.
     */
    boolean isCostShown();

    /**
     * notify the presenter when the node at {@code clickedNodePosition} is clicked
     */
    void onNodeClicked(IPosition clickedNodePosition);

    /**
     * notify the presenter when there is a dragging gesture detected over the node at position {@code draggedOverNodePosition}.
     */
    void onNodeDragOver(IPosition draggedOverNodePosition);

    /**
     * notify the presenter when the mouse pointer is over the node at {@code hoverNodePosition}.
     */
    void onNodeHover(IPosition hoverNodePosition);

    /**
     * notify the presenter when the node at {@code draggedNodePosition} is being dragged, the drag gesture needs to start at the mentioned node.
     */
    void onNodeDragged(IPosition draggedNodePosition);

    void onGridViewUpdated(Pane updatedView);
}
