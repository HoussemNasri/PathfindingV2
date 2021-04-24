package tech.houssemnasri.grid;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.input.MouseEvent;

import tech.houssemnasri.node.Position;
import tech.houssemnasri.mvp.Presenter;
import tech.houssemnasri.node.INode;
import tech.houssemnasri.toolbox.IToolbox;

public interface IGridPresenter extends Presenter<IGridView> {
  int getRows();

  IntegerProperty rowsProperty();

  int getColumns();

  IntegerProperty colsProperty();

  Position getSourcePosition();

  ObjectProperty<Position> sourcePositionProperty();

  Position getDestinationPosition();

  ObjectProperty<Position> destinationPositionProperty();

  void setGridModel(IGrid gridModel);

  void setToolboxModel(IToolbox toolbox);

  /** Returns the node model at position {@code position} */
  INode getNodeModel(Position position);

  /** notify the presenter when the node at {@code clickedNodePosition} is clicked */
  void onNodeClicked(MouseEvent mouseEvent, Position clickedNodePosition);

  /**
   * notify the presenter when there is a dragging gesture detected on grid.
   *
   * @param mouseEvent the mouse event of the drag gesture
   * @param intersectedNodePosition the position of the node intersected with the drag gesture.
   */
  void onGridDragged(MouseEvent mouseEvent, Position intersectedNodePosition);

  /** notify the presenter when the mouse pointer is over the node at {@code hoverNodePosition}. */
  void onNodeHover(Position hoverNodePosition);

  void onNodePressed(MouseEvent mouseEvent, Position intersectedNodePosition);

  void onMouseRelease(MouseEvent mouseEvent, Position releaseNodePosition);
}
