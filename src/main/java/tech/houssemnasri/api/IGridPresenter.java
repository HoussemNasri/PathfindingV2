package tech.houssemnasri.api;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.Pane;

public interface IGridPresenter {
    // The presenter manages the state of the view

    /** Setting and updating the current theme. */
    void setTheme(ITheme newTheme);

    /** Returns the currently used theme. */
    ITheme getTheme();

    ObjectProperty<ITheme> themeObjectProperty();

    /** Set the size of all nodes on the grid to {@code nodeSize} all re-render grid. */
    void setNodeSize(INodeView.NodeSize nodeSize);

    INodeView.NodeSize getNodeSize();

    ObjectProperty<INodeView.NodeSize> nodeSizeObjectProperty();

    int getRows();

    IntegerProperty rowsProperty();

    int getColumns();

    IntegerProperty colsProperty();

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

    // The presenter takes actions according to the user’s input notification from the View.

    /** notify the presenter when the node at {@code clickedNodePosition} is clicked */
    void onNodeClicked(IPosition clickedNodePosition);

    /**
     * notify the presenter when there is a dragging gesture detected over the node at position
     * {@code draggedOverNodePosition}.
     */
    void onNodeDragOver(IPosition draggedOverNodePosition);

    /**
     * notify the presenter when the mouse pointer is over the node at {@code hoverNodePosition}.
     */
    void onNodeHover(IPosition hoverNodePosition);

    /**
     * notify the presenter when the node at {@code draggedNodePosition} is being dragged, the drag
     * gesture needs to start at the mentioned node.
     */
    void onNodeDragged(IPosition draggedNodePosition);
}
