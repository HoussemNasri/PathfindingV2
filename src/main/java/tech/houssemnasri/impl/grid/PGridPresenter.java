package tech.houssemnasri.impl.grid;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import tech.houssemnasri.Clamp;
import tech.houssemnasri.api.grid.IGrid;
import tech.houssemnasri.api.grid.IGridPresenter;
import tech.houssemnasri.api.grid.IGridView;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.node.IPosition;
import tech.houssemnasri.api.theme.ITheme;
import tech.houssemnasri.impl.node.PPosition;
import tech.houssemnasri.property.ComplexIntegerProperty;
import tech.houssemnasri.property.ComplexObjectProperty;

/** The P in MVP */
public class PGridPresenter implements IGridPresenter {
    /** The minimum scale zoom out can reach. */
    private static final double MIN_SCALE = 1;
    /** The maximum scale zoom in can reach. */
    private static final double MAX_SCALE = 10;
    /** The amount of which the scale increase or decrease each zoomIn or zoomOut */
    private static final double SCALE_DELTA = 1.2;

    private final ObjectProperty<ITheme> themeProperty = new ComplexObjectProperty<>();
    private final IntegerProperty rowsProperty = new ComplexIntegerProperty();
    private final IntegerProperty colsProperty = new ComplexIntegerProperty();
    private final DragContext sceneDragContext = new DragContext();

    /** The M in MVP */
    private IGrid gridModel;

    /** The V in MVP */
    private IGridView gridView;

    public PGridPresenter(IGrid gridModel, IGridView gridView, ITheme theme) {
        setGridModel(gridModel);
        setGridView(gridView);
        bindColsPropertyToModel();
        bindRowsPropertyToModel();
        setTheme(theme);
    }

    private void bindColsPropertyToModel() {
        colsProperty.bind(gridModel.columnsProperty());
    }

    private void bindRowsPropertyToModel() {
        rowsProperty.bind(gridModel.rowsProperty());
    }

    private void setGridModel(IGrid gridModel) {
        this.gridModel = gridModel;
    }

    private void setGridView(IGridView gridView) {
        this.gridView = gridView;
        this.gridView.setPresenter(this);
    }

    @Override
    public void setTheme(ITheme newTheme) {
        if (newTheme == null) return;
        themeProperty.set(newTheme);
    }

    @Override
    public ITheme getTheme() {
        return themeProperty.get();
    }

    @Override
    public ObjectProperty<ITheme> themeObjectProperty() {
        return themeProperty;
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
    public IPosition getSourcePosition() {
        return gridModel.getSourcePosition();
    }

    @Override
    public ObjectProperty<IPosition> sourcePositionProperty() {
        return gridModel.sourceNodePositionProperty();
    }

    @Override
    public IPosition getDestinationPosition() {
        return gridModel.getDestinationPosition();
    }

    @Override
    public ObjectProperty<IPosition> destinationPositionProperty() {
        return gridModel.destinationNodePositionProperty();
    }

    @Override
    public INode getNodeModel(IPosition position) {
        return gridModel.getNode(position);
    }

    @Override
    public boolean zoomIn() {
        double scale = gridView.getScale() * SCALE_DELTA;
        gridView.setScale(new Clamp(scale).apply(MIN_SCALE, MAX_SCALE), true);
        return false;
    }

    @Override
    public boolean zoomOut() {
        double scale = gridView.getScale() / SCALE_DELTA;
        gridView.setScale(new Clamp(scale).apply(MIN_SCALE, MAX_SCALE), true);
        return false;
    }

    @Override
    public void onNodeClicked(MouseEvent mouseEvent, IPosition clickedNodePosition) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            doDrawWall(clickedNodePosition);
        }
    }

    private void doDrawWall(IPosition clickedNodePosition) {
        if (not(clickedNodePosition.equals(PPosition.ERROR))) {
            INode clickedNode = gridModel.getNode(clickedNodePosition);
            if (clickedNode.getType() == INode.Type.BASIC) {
                clickedNode.setType(INode.Type.WALL);
            }
        }
    }

    public void doDragGrid(MouseEvent event) {
        if (!event.isSecondaryButtonDown()) return;
        Node rootView = gridView.getRoot();

        double transX =
                sceneDragContext.translateAnchorX
                        + event.getSceneX()
                        - sceneDragContext.mouseAnchorX;
        double transY =
                sceneDragContext.translateAnchorY
                        + event.getSceneY()
                        - sceneDragContext.mouseAnchorY;
        rootView.setTranslateX(transX);
        rootView.setTranslateY(transY);
    }

    /** Helper method for better readability */
    private boolean not(boolean bool) {
        return !bool;
    }

    @Override
    public void onGridDragged(MouseEvent mouseEvent, IPosition intersectedNodePosition) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            doDrawWall(intersectedNodePosition);
        } else {
            doDragGrid(mouseEvent);
        }
    }

    @Override
    public void onNodeHover(IPosition hoverNodePosition) {}

    @Override
    public void onSourceNodeDragged(MouseEvent mouseEvent) {}

    @Override
    public void onDestinationNodeDragged(MouseEvent mouseEvent) {}

    @Override
    public void onScroll(ScrollEvent scrollEvent) {
        if (scrollEvent.getDeltaY() < 0) {
            zoomOut();
        } else {
            zoomIn();
        }
    }

    @Override
    public void onNodePressed(MouseEvent mouseEvent, IPosition intersectedNodePosition) {
        // right mouse button => panning
        if (!mouseEvent.isSecondaryButtonDown()) return;

        sceneDragContext.mouseAnchorX = mouseEvent.getSceneX();
        sceneDragContext.mouseAnchorY = mouseEvent.getSceneY();

        sceneDragContext.translateAnchorX = gridView.getRoot().getTranslateX();
        sceneDragContext.translateAnchorY = gridView.getRoot().getTranslateY();
    }

    /** Mouse drag context used for scene and nodes. */
    private static class DragContext {

        double mouseAnchorX;
        double mouseAnchorY;

        double translateAnchorX;
        double translateAnchorY;
    }
}
