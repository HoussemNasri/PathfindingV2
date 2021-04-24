package tech.houssemnasri.grid;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import tech.houssemnasri.BooleanExtensions;
import tech.houssemnasri.node.INode;
import tech.houssemnasri.toolbox.IToolbox;
import tech.houssemnasri.node.Position;
import tech.houssemnasri.toolbox.Toolbox;
import tech.houssemnasri.property.ComplexIntegerProperty;

/** The P in MVP */
public class PGridPresenter implements IGridPresenter, BooleanExtensions {
  private boolean isDraggingSourceNode = false;
  private boolean isDraggingDestinationNode = false;

  private final IntegerProperty rowsProperty = new ComplexIntegerProperty();
  private final IntegerProperty colsProperty = new ComplexIntegerProperty();

  /** The M in MVP */
  private IGrid gridModel;

  /** The V in MVP */
  private IGridView gridView;

  private IToolbox toolboxModel;

  public PGridPresenter(IGrid gridModel, IGridView gridView, IToolbox toolboxModel) {
    setGridModel(gridModel);
    setView(gridView);
    setToolboxModel(toolboxModel);
  }

  public PGridPresenter() {
    this(null, null, new Toolbox());
  }

  private void bindColsPropertyToModel() {
    if (gridModel == null) return;
    colsProperty.bind(gridModel.columnsProperty());
  }

  private void bindRowsPropertyToModel() {
    if (gridModel == null) return;
    rowsProperty.bind(gridModel.rowsProperty());
  }

  @Override
  public void setGridModel(IGrid gridModel) {
    this.gridModel = gridModel;
    bindColsPropertyToModel();
    bindRowsPropertyToModel();
  }

  @Override
  public void setToolboxModel(IToolbox toolbox) {
    if (toolbox == null) return;
    this.toolboxModel = toolbox;
  }

  @Override
  public int getRows() {
    return rowsProperty.get();
  }

  @Override
  public IntegerProperty rowsProperty() {
    return rowsProperty;
  }

  @Override
  public int getColumns() {
    return colsProperty.get();
  }

  @Override
  public IntegerProperty colsProperty() {
    return colsProperty;
  }

  @Override
  public Position getSourcePosition() {
    return gridModel.getSourcePosition();
  }

  @Override
  public ObjectProperty<Position> sourcePositionProperty() {
    return gridModel.sourceNodePositionProperty();
  }

  @Override
  public Position getDestinationPosition() {
    return gridModel.getDestinationPosition();
  }

  @Override
  public ObjectProperty<Position> destinationPositionProperty() {
    return gridModel.destinationNodePositionProperty();
  }

  @Override
  public INode getNodeModel(Position position) {
    return gridModel.getNode(position);
  }

  @Override
  public void onNodeClicked(MouseEvent mouseEvent, Position clickedNodePosition) {
    if (mouseEvent.getButton() == MouseButton.PRIMARY) {
      doEditWall(clickedNodePosition);
    }
  }

  private void doEditWall(Position clickedNodePosition) {
    if (not(clickedNodePosition.equals(Position.ERROR))) {
      INode clickedNode = gridModel.getNode(clickedNodePosition);
      if (toolboxModel.getWallDrawMode() == IToolbox.WallDrawMode.DRAW) {
        if (clickedNode.getType() == INode.Type.BASIC) {
          clickedNode.setType(INode.Type.WALL);
        }
      } else {
        if (clickedNode.getType() == INode.Type.WALL) {
          clickedNode.setType(INode.Type.BASIC);
        }
      }
    }
  }

  @Override
  public void onGridDragged(MouseEvent mouseEvent, Position intersection) {
    if (mouseEvent.getButton() == MouseButton.PRIMARY) {
      if (isDraggingSourceNode) {
        doRelocateSourceTo(intersection);
      } else if (isDraggingDestinationNode) {
        doRelocateDestinationTo(intersection);
      } else {
        doEditWall(intersection);
      }
    }
  }

  private void doRelocateDestinationTo(Position intersection) {
    if (and(
        not(intersection.equals(Position.ERROR)),
        gridModel.isWalkable(intersection),
        not(gridModel.isSourceNode(gridModel.getNode(intersection))),
        not(toolboxModel.isDraggingNodesLocked()))) {
      gridModel.relocateDestination(intersection);
    }
  }

  private void doRelocateSourceTo(Position intersection) {
    if (and(
        not(intersection.equals(Position.ERROR)),
        gridModel.isWalkable(intersection),
        not(gridModel.isDestinationNode(gridModel.getNode(intersection))),
        not(toolboxModel.isDraggingNodesLocked()))) {
      gridModel.relocateSource(intersection);
    }
  }

  @Override
  public void onNodeHover(Position hoverNodePosition) {}

  @Override
  public void onNodePressed(MouseEvent mouseEvent, Position intersection) {
    if (and(mouseEvent.isPrimaryButtonDown(), not(intersection.equals(Position.ERROR)))) {
      if (gridModel.isSourceNode(gridModel.getNode(intersection))) {
        isDraggingSourceNode = true;
      } else if (gridModel.isDestinationNode(gridModel.getNode(intersection))) {
        isDraggingDestinationNode = true;
      }
    }
  }

  @Override
  public void onMouseRelease(MouseEvent mouseEvent, Position releaseNodePosition) {
    isDraggingSourceNode = false;
    isDraggingDestinationNode = false;
  }

  @Override
  public void setView(IGridView view) {
    if (view == null) return;
    this.gridView = view;
    this.gridView.setPresenter(this);
  }
}
