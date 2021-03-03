package tech.houssemnasri.impl;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import tech.houssemnasri.api.IGridPresenter;
import tech.houssemnasri.api.IGridView;
import tech.houssemnasri.api.INodeView;
import tech.houssemnasri.api.IPosition;

public class PGridView implements IGridView {
    private final ScaleTransition scaleTransition = new ScaleTransition();

    private static PGridView INSTANCE = null;

    private IGridPresenter presenter = null;
    private final GridPane root = new GridPane();

    public PGridView(IGridPresenter presenter) {
        setPresenter(presenter);
        initRoot();
        listenForMouseClicks();
        listenForMouseDrags();
        listenForScrollEvent();
        initScaleTransition();
    }

    private void initScaleTransition() {
        scaleTransition.setDuration(Duration.millis(295));
        scaleTransition.setNode(root);
        scaleTransition.setInterpolator(Interpolator.EASE_IN);
    }

    private PGridView() {
        this(null);
    }

    private void listenForMouseDrags() {
        root.setOnMouseDragged(e -> presenter.onNodeDragOver(findIntersectedNodePosition(e)));
    }

    private void listenForMouseClicks() {
        root.setOnMouseClicked(e -> presenter.onNodeClicked(findIntersectedNodePosition(e)));
    }

    private void listenForScrollEvent() {
        root.setOnScroll(e -> presenter.onScroll(e));
    }

    /**
     * Returns the {@code IPosition} of the intersected node. Returns {@code PPosition.ERROR} if
     * position out of logical bounds.
     */
    private IPosition findIntersectedNodePosition(MouseEvent mouseEvent) {
        Node intersectedNode = mouseEvent.getPickResult().getIntersectedNode();
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
                INodeView thisNodeView = new PNodeView(presenter.getNodeModel(position));
                bindTheme(thisNodeView);

                StackPane realNodeView = (StackPane) thisNodeView;
                GridPane.setColumnIndex(realNodeView, x);
                GridPane.setRowIndex(realNodeView, y);
                root.add(realNodeView, x, y);
            }
        }
    }

    /** Binding {@code nodeView} theme to presenter's theme */
    private void bindTheme(INodeView nodeView) {
        nodeView.themeProperty().bind(presenter.themeObjectProperty());
    }

    @Override
    public Node getRoot() {
        refresh();
        return root;
    }

    @Override
    public void showCostInfo(boolean show) {}

    @Override
    public void setPresenter(IGridPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setScale(double scale, boolean animate) {
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
