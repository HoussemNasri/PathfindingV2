package tech.houssemnasri.api.grid;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.node.IPosition;
import tech.houssemnasri.api.theme.ITheme;

public interface IGridPresenter {
    // The presenter manages the state of the view

    /** Setting and updating the current theme. */
    void setTheme(ITheme newTheme);

    /** Returns the currently used theme. */
    ITheme getTheme();

    ObjectProperty<ITheme> themeObjectProperty();

    int getRows();

    IntegerProperty rowsProperty();

    int getColumns();

    IntegerProperty colsProperty();

    /** Returns the node model at position {@code position} */
    INode getNodeModel(IPosition position);

    /**
     * Zoom in, and increase node size.
     *
     * @return True if the zoom happened and we didn't reach our maximum zoom level.
     */
    boolean zoomIn();

    /**
     * Zoom out, and decrease node size.
     *
     * @return True if the zoom happened and we didn't reach our minimum zooming level.
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
     * notify the presenter when the source node is being dragged, the drag gesture needs to start
     * at the source node.
     */
    void onSourceNodeDragged(MouseEvent mouseEvent);
    /**
     * notify the presenter when the destination node is being dragged, the drag gesture needs to
     * start at the destination node.
     */
    void onDestinationNodeDragged(MouseEvent mouseEvent);

    void onGridDragged(MouseEvent mouseEvent);

    /**
     * Defines a function to be called when user performs a scrolling action.
     *
     * @param scrollEvent the scroll event
     */
    void onScroll(ScrollEvent scrollEvent);
}
