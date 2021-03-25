package tech.houssemnasri.impl.node.painter;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import tech.houssemnasri.api.node.INodeView;
import tech.houssemnasri.api.theme.ITheme;

import static tech.houssemnasri.api.node.INode.*;

public class PNodePainter extends BaseNodePainter{

    public PNodePainter(ITheme theme, INodeView nodeView) {
        super(nodeView, theme);
        nodeView.showCostProperty().addListener((observable, oldValue, newValue) -> setShowCost(newValue));
    }

    @Override
    public void paint(Type nodeType) {
        //At startup we might not have the theme ready so we need to wait for
        // the presenter to send us the theme, and execute the theming code at {code changeTheme()}
        if(getTheme() == null) return;
        switch (nodeType){
            case BASIC -> paintBasic();
            case OPEN -> paintOpen();
            case CLOSED -> paintClosed();
            case WALL -> paintWall();
            case PATH -> paintPath();
            case SOURCE -> paintSource();
            case DESTINATION -> paintDestination();
        }
    }

    private void setShowCost(boolean show){
        if(!getNodeView().isShowCostEnabled() && show){
            return;
        }
        getNodeView().getCenterText().setVisible(show);
        getNodeView().getTopLeftCornerText().setVisible(show);
        getNodeView().getTopRightCornerText().setVisible(show);
    }

    private void paintDestination() {
        setBackgroundColor(getTheme().getDestinationNodeColor());
        setShowCost(false);
    }

    private void paintSource() {
        setBackgroundColor(getTheme().getSourceNodeColor());
        setShowCost(false);
    }

    private void paintBasic() {
        setBackgroundColor(getTheme().getBasicNodeColor());
        setShowCost(false);
    }

    private void paintOpen() {
        setBackgroundColor(getTheme().getOpenNodeColor());
        setShowCost(true);
    }

    private void paintClosed() {
        setBackgroundColor(getTheme().getClosedNodeColor());
        setShowCost(true);
    }

    private void paintWall() {
        setBackgroundColor(getTheme().getWallNodeColor());
        setShowCost(false);
    }

    private void paintPath() {
        setBackgroundColor(getTheme().getPathNodeColor());
        setShowCost(true);
    }

    private void setBackgroundColor(Color color) {
        Background coloredBackground = new Background(new BackgroundFill(color, null, null));
        getNodeView().getRoot().setBackground(coloredBackground);
    }

    @Override
    public BaseNodePainter create(INodeView nodeView) {
        return new PNodePainter(getTheme(), nodeView);
    }
}
