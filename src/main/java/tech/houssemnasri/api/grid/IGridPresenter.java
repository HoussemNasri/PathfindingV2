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

    void onNodePressed(MouseEvent mouseEvent, IPosition intersectedNodePosition);

    void onMouseRelease(MouseEvent mouseEvent, IPosition releaseNodePosition);
}
