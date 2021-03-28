package tech.houssemnasri.impl.grid;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import tech.houssemnasri.api.grid.IGridPresenter;
import tech.houssemnasri.api.grid.IGridView;
import tech.houssemnasri.api.node.INodeView;
import tech.houssemnasri.api.node.IPosition;
import tech.houssemnasri.gesturefx.GesturePane;
import tech.houssemnasri.impl.node.PNodeView;
import tech.houssemnasri.impl.node.Position;
import tech.houssemnasri.impl.node.painter.PNodePainter;

public class PGridView implements IGridView {
  private static PGridView INSTANCE = null;

  private IGridPresenter presenter = null;
  private final GridPane root = new GridPane();
  private final GesturePane rootWrapper = new GesturePane(root);

  public PGridView(IGridPresenter presenter) {
    // Init
    initRoot();
    initRootWrapper();
    setPresenter(presenter);

    // Listen For Mouse Events
    listenForMouseClicks();
    listenForMouseDrags();
    listenForMousePress();
    listenForMouseRelease();
    listenForGapChange();
  }

  private void listenForGapChange() {
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

  private void listenForMouseRelease() {
    root.setOnMouseReleased(e -> presenter.onMouseRelease(e, findIntersectedNodePosition(e)));
  }

  private void listenForMouseDrags() {
    root.setOnMouseDragged(e -> presenter.onGridDragged(e, findIntersectedNodePosition(e)));
  }

  private void listenForMouseClicks() {
    root.setOnMouseClicked(e -> presenter.onNodeClicked(e, findIntersectedNodePosition(e)));
  }

  private void listenForMousePress() {
    root.setOnMousePressed(e -> presenter.onNodePressed(e, findIntersectedNodePosition(e)));
  }

  private void listenForThemeChange() {
    presenter.themeObjectProperty().addListener(this::onThemeChanged);
  }

  private void onThemeChanged(Observable observable) {
    int cols = presenter.getColumns();
    int rows = presenter.getRows();
    for (int x = 0; x < cols; x++) {
      for (int y = 0; y < rows; y++) {
        INodeView thisNodeView = getNodeAtPosition(Position.of(x, y));
        if (thisNodeView != null) {
          thisNodeView.getPainter().switchTheme();
        }
      }
    }
  }

  public INodeView getNodeAtPosition(IPosition position) {
    int row = position.getY();
    int column = position.getX();
    ObservableList<Node> children = root.getChildren();
    for (Node node : children) {
      if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
        return (INodeView) node;
      }
    }
    return null;
  }

  /**
   * Returns the {@code IPosition} of the intersected node. Returns {@code PPosition.ERROR} if
   * position out of logical bounds.
   */
  private IPosition findIntersectedNodePosition(MouseEvent mouseEvent) {
    Node intersection = mouseEvent.getPickResult().getIntersectedNode();
    // The intersected node might not be the NodeView itself but it's child
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

  private void initRoot() {
    root.getStyleClass().add("grid");
  }

  private void computeGridSize(int cols, int rows) {
    double gap = root.getHgap();
    double gridWidth = cols * PNodeView.INITIAL_NODE_SIZE + (cols + 1) * gap;
    double gridHeight = rows * PNodeView.INITIAL_NODE_SIZE + (rows + 1) * gap;
    root.setMinSize(gridWidth, gridHeight);
  }

  @Override
  public void refresh() {
    root.getChildren().clear();
    int cols = presenter.getColumns();
    int rows = presenter.getRows();
    for (int x = 0; x < cols; x++) {
      for (int y = 0; y < rows; y++) {
        IPosition position = Position.of(x, y);
        INodeView thisNode = new PNodeView(presenter.getNodeModel(position));
        thisNode.setPainter(new PNodePainter(thisNode));
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
  public void setShowCostInfo(boolean show) {
    int cols = presenter.getColumns();
    int rows = presenter.getRows();
    for (int x = 0; x < cols; x++) {
      for (int y = 0; y < rows; y++) {
        INodeView thisNodeView = getNodeAtPosition(Position.of(x, y));
        if (thisNodeView != null) {
          thisNodeView.setShowCostInfo(show);
        }
      }
    }
  }

  @Override
  public void setPresenter(IGridPresenter presenter) {
    if (presenter == null) {
      return;
    }
    this.presenter = presenter;
    refresh();
    listenForThemeChange();
  }

  public IGridPresenter getPresenter() {
    return presenter;
  }

  public static PGridView getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new PGridView();
    }
    return INSTANCE;
  }
}
