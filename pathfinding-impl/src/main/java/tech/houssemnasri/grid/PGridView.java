package tech.houssemnasri.grid;

import javafx.beans.Observable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import tech.houssemnasri.gesturefx.GesturePane;
import tech.houssemnasri.node.INodeView;
import tech.houssemnasri.node.PNodeView;
import tech.houssemnasri.Position;
import tech.houssemnasri.node.skins.BaseNodeSkin;

public class PGridView implements IGridView {
  private IGridPresenter presenter = null;
  private final GridPane root = new GridPane();
  private final GesturePane rootWrapper = new GesturePane(root);

  public PGridView(IGridPresenter presenter) {
    root.getStyleClass().setAll("grid");
    initRootWrapper();
    setPresenter(presenter);

    // Listen For Mouse Events
    handleMouseClicked();
    handleMouseDragged();
    handleMousePressed();
    handleMouseReleased();
    handleGapSizeChanged();
  }

  private void handleGapSizeChanged() {
    root.hgapProperty()
        .addListener(e -> computeGridSize(root.getColumnCount(), root.getRowCount()));
  }

  public PGridView() {
    this(null);
  }

  private void initRootWrapper() {
    rootWrapper.sceneProperty().addListener(this::doHandleSceneChange);
    rootWrapper.setGestureEnabled(true);
    rootWrapper.setScrollMode(GesturePane.ScrollMode.ZOOM);
    rootWrapper.setFitMode(GesturePane.FitMode.COVER);
    rootWrapper.setFitWidth(true);
    rootWrapper.setFitHeight(true);
    rootWrapper.setMinScale(1);
    rootWrapper.setScrollBarPolicy(GesturePane.ScrollBarPolicy.NEVER);
  }

  private void doHandleSceneChange(Observable o) {
    if (this.rootWrapper.getScene() != null) {
      this.rootWrapper.prefWidthProperty().bind(rootWrapper.getScene().widthProperty());
      this.rootWrapper.prefHeightProperty().bind(rootWrapper.getScene().heightProperty());
    }
  }

  private GesturePane getRootWrapper() {
    return this.rootWrapper;
  }

  private void handleMouseReleased() {
    root.addEventFilter(
        MouseEvent.MOUSE_RELEASED,
        e -> presenter.onMouseRelease(e, findIntersectedNodePosition(e)));
  }

  private void handleMouseDragged() {
    root.addEventFilter(
        MouseEvent.MOUSE_DRAGGED, e -> presenter.onGridDragged(e, findIntersectedNodePosition(e)));
  }

  private void handleMouseClicked() {
    root.addEventFilter(
        MouseEvent.MOUSE_CLICKED, e -> presenter.onNodeClicked(e, findIntersectedNodePosition(e)));
  }

  private void handleMousePressed() {
    root.addEventFilter(
        MouseEvent.MOUSE_PRESSED, e -> presenter.onNodePressed(e, findIntersectedNodePosition(e)));
  }

  /**
   * Returns the {@code Position} of the intersected node. Returns {@code PPosition.ERROR} if
   * position out of logical bounds.
   */
  private Position findIntersectedNodePosition(MouseEvent mouseEvent) {
    Node intersection = mouseEvent.getPickResult().getIntersectedNode();
    // The intersected node might not be the PNodeView itself but it's child
    while (intersection != null) {
      Integer eventXPosition = GridPane.getColumnIndex(intersection);
      Integer eventYPosition = GridPane.getRowIndex(intersection);
      if (eventXPosition != null && eventYPosition != null) {
        return Position.of(eventXPosition, eventYPosition);
      }
      intersection = intersection.getParent();
    }
    return Position.ERROR;
  }

  private void computeGridSize(int cols, int rows) {
    double gap = root.getHgap();
    double gridWidth = cols * BaseNodeSkin.PREF_WIDTH + (cols + 1) * gap;
    double gridHeight = rows * BaseNodeSkin.PREF_WIDTH + (rows + 1) * gap;
    root.setMinSize(gridWidth, gridHeight);
  }

  @Override
  public void refresh() {
    root.getChildren().clear();
    int cols = presenter.getColumns();
    int rows = presenter.getRows();
    for (int x = 0; x < cols; x++) {
      for (int y = 0; y < rows; y++) {
        Position position = Position.of(x, y);
        INodeView thisNode = new PNodeView(presenter.getNodeModel(position), true);
        GridPane.setColumnIndex(thisNode.getRoot(), x);
        GridPane.setRowIndex(thisNode.getRoot(), y);
        root.add(thisNode.getRoot(), x, y);
      }
    }
  }

  @Override
  public Region getRoot() {
    return getRootWrapper();
  }

  @Override
  public void setPresenter(IGridPresenter presenter) {
    if (presenter == null) {
      return;
    }
    this.presenter = presenter;
    refresh();
  }

  public IGridPresenter getPresenter() {
    return presenter;
  }
}
