package tech.houssemnasri.impl.grid;

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
import tech.houssemnasri.impl.animation.AnimationSuite;
import tech.houssemnasri.impl.node.PNodeView;
import tech.houssemnasri.impl.node.Position;
import tech.houssemnasri.impl.node.painter.AnimatedNodePainter;

public class PGridView implements IGridView {
  private static PGridView INSTANCE = null;

  private IGridPresenter presenter = null;
  private final GridPane root = new GridPane();

  public PGridView(IGridPresenter presenter) {
    // Init
    setPresenter(presenter);
    initRoot();

    // Listen For Mouse Events
    listenForMouseClicks();
    listenForMouseDrags();
    listenForMousePress();
    listenForMouseRelease();
  }

  private PGridView() {
    this(null);
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
          thisNodeView.getPainter().switchTheme(presenter.getTheme());
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
    root.setHgap(1.5);
    root.setVgap(1.5);
    root.setSnapToPixel(false);
    root.setStyle("-fx-background-color: gray");
    root.setPadding(new Insets(1.5));
  }

  private void computeGridSize(int cols, int rows) {
    double gridWidth = cols * PNodeView.INITIAL_NODE_SIZE + (cols + 1) * root.getHgap();
    double gridHeight = rows * PNodeView.INITIAL_NODE_SIZE + (rows + 1) * root.getVgap();
    root.setMinSize(gridWidth, gridHeight);
  }

  @Override
  public Region refresh() {
    root.getChildren().clear();
    int cols = presenter.getColumns();
    int rows = presenter.getRows();
    computeGridSize(cols, rows);
    for (int x = 0; x < cols; x++) {
      for (int y = 0; y < rows; y++) {
        IPosition position = Position.of(x, y);
        INodeView thisNode = new PNodeView(presenter.getNodeModel(position));
        thisNode.setPainter(
            new AnimatedNodePainter(
                thisNode, getPresenter().getTheme(), new AnimationSuite.Builder().build()));
        GridPane.setColumnIndex(thisNode.getRoot(), x);
        GridPane.setRowIndex(thisNode.getRoot(), y);
        root.add(thisNode.getRoot(), x, y);
      }
    }
    return root;
  }

  @Override
  public Region getRoot() {
    return createGesturePane();
  }

  private GesturePane createGesturePane() {
    GesturePane rootWrapper = new GesturePane(root);
    rootWrapper.setGestureEnabled(true);
    rootWrapper.setScrollMode(GesturePane.ScrollMode.ZOOM);
    rootWrapper.setFitMode(GesturePane.FitMode.COVER);
    rootWrapper.setFitWidth(true);
    rootWrapper.setFitHeight(true);
    rootWrapper.setMinScale(1);
    rootWrapper.setScrollBarPolicy(GesturePane.ScrollBarPolicy.NEVER);
    return rootWrapper;
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
