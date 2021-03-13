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

    IPosition getSourcePosition();

    ObjectProperty<IPosition> sourcePositionProperty();

    IPosition getDestinationPosition();

    ObjectProperty<IPosition> destinationPositionProperty();

    /** Returns the node model at position {@code position} */
    INode getNodeModel(IPosition position);

    /**
     * Zoom in, and increase node size.
     */
    void zoomIn();

    /**
     * Zoom out, and decrease node size.
     */
    void zoomOut();

    // The presenter takes actions according to the user’s input notification from the View.

    /** notify the presenter when the node at {@code clickedNodePosition} is clicked */
    void onNodeClicked(MouseEvent mouseEvent, IPosition clickedNodePosition);

    /**
     * notify the presenter when there is a dragging gesture detected on grid.
     *
     * @param mouseEvent the mouse event of the drag gesture
     * @param intersectedNodePosition the position of the node intersected with the drag gesture.
     */
    void onGridDragged(MouseEvent mouseEvent, IPosition intersectedNodePosition);

    /**
     * notify the presenter when the mouse pointer is over the node at {@code hoverNodePosition}.
     */
    void onNodeHover(IPosition hoverNodePosition);

    /**
     * Defines a function to be called when user performs a scrolling action.
     *
     * @param scrollEvent the scroll event
     */
    void onScroll(ScrollEvent scrollEvent);

    void onNodePressed(MouseEvent mouseEvent, IPosition intersectedNodePosition);

    void onMouseRelease(MouseEvent mouseEvent, IPosition releaseNodePosition);
}
