package tech.houssemnasri.impl.grid;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import tech.houssemnasri.api.grid.IGridPresenter;
import tech.houssemnasri.api.grid.IGridView;
import tech.houssemnasri.api.node.INodeView;
import tech.houssemnasri.api.node.IPosition;
import tech.houssemnasri.impl.animation.AnimationSuite;
import tech.houssemnasri.impl.node.PNodeView;
import tech.houssemnasri.impl.node.PPosition;
import tech.houssemnasri.impl.node.painter.AnimatedNodePainter;

public class PGridView implements IGridView {
    private static final EventHandler<? super MouseEvent> REMOVE_LISTENER = null;
    private final ScaleTransition scaleTransition = new ScaleTransition();

    private static PGridView INSTANCE = null;

    private IGridPresenter presenter = null;
    private final GridPane root = new GridPane();

    public PGridView(IGridPresenter presenter) {
        // Init
        setPresenter(presenter);
        initRoot();
        initScaleTransition();

        // Listen For Mouse Events
        listenForMouseClicks();
        listenForMouseDrags();
        listenForScrollEvent();
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

    private void listenForScrollEvent() {
        root.setOnScroll(e -> presenter.onScroll(e));
    }

    private void listenForMousePress() {
        root.setOnMousePressed(e -> presenter.onNodePressed(e, findIntersectedNodePosition(e)));
    }

    private void initScaleTransition() {
        scaleTransition.setNode(root);
        scaleTransition.setInterpolator(Interpolator.EASE_IN);
    }

    private void listenForThemeChange() {
        presenter.themeObjectProperty().addListener(this::onThemeChanged);
    }

    private void onThemeChanged(Observable observable) {
        int cols = presenter.getColumns();
        int rows = presenter.getRows();
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                INodeView thisNodeView = getNodeAtPosition(PPosition.of(x, y));
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
        Node intersectedNode = mouseEvent.getPickResult().getIntersectedNode();
        if (intersectedNode == null) {
            return PPosition.ERROR;
        }
        Integer eventXPosition = GridPane.getColumnIndex(intersectedNode);
        Integer eventYPosition = GridPane.getRowIndex(intersectedNode);
        if (eventXPosition != null && eventYPosition != null) {
            return PPosition.of(eventXPosition, eventYPosition);
        }
        return PPosition.ERROR;
    }

    private void initRoot() {
        root.setHgap(1.5);
        root.setVgap(1.5);
        root.setSnapToPixel(false);
        root.setStyle("-fx-background-color: gray");
        root.setMinSize(300, 300);
        root.setPadding(new Insets(1.5));
    }

    @Override
    public void refresh() {
        root.getChildren().clear();
        int cols = presenter.getColumns();
        int rows = presenter.getRows();
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                IPosition position = PPosition.of(x, y);
                PNodeView thisNodeView = new PNodeView(presenter.getNodeModel(position));
                thisNodeView.setPainter(
                        new AnimatedNodePainter(
                                thisNodeView,
                                getPresenter().getTheme(),
                                new AnimationSuite.Builder().build()));
                GridPane.setColumnIndex(thisNodeView, x);
                GridPane.setRowIndex(thisNodeView, y);
                root.add(thisNodeView, x, y);
            }
        }
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void showCostInfo(boolean show) {}

    @Override
    public void setPresenter(IGridPresenter presenter) {
        if (presenter == null) {
            return;
        }
        this.presenter = presenter;
        listenForThemeChange();
    }

    @Override
    public void setScale(double scale, boolean animate) {
        if (scaleTransition.getStatus() == Animation.Status.RUNNING) {
            return;
        }
        scaleTransition.setDuration(animate ? Duration.millis(300) : Duration.ONE);
        scaleTransition.setToX(scale);
        scaleTransition.setToY(scale);
        scaleTransition.play();
    }

    @Override
    public double getScale() {
        return root.getScaleX();
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
