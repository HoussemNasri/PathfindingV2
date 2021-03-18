package tech.houssemnasri.impl.node.painter;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import tech.houssemnasri.api.theme.ITheme;
import tech.houssemnasri.impl.node.PNodeView;

import static tech.houssemnasri.api.node.INode.*;

/** This class {@code NodePainter} is responsible for painting {@code PNodeView} according to it's type and the current theme. */
public class SimpleNodePainter extends BaseNodePainter{

    public SimpleNodePainter(ITheme theme, PNodeView nodeView) {
        super(nodeView, theme);
    }

    @Override
    public void paint(Type nodeType) {
        //At startup we might not have the theme ready so we need to wait for
        // the presenter to send us the theme, and execute the theming code at {code changeTheme()}
        if(getTheme() == null) return;
        switch (nodeType){
            case BASIC -> paintBasic(getNodeView());
            case OPEN -> paintOpen(getNodeView());
            case CLOSED -> paintClosed(getNodeView());
            case WALL -> paintWall(getNodeView());
            case PATH -> paintPath(getNodeView());
            case SOURCE -> paintSource(getNodeView());
            case DESTINATION -> paintDestination(getNodeView());
        }
    }

    private void paintDestination(PNodeView nodeView) {
        setBackgroundColor(nodeView, getTheme().getDestinationNodeColor());
    }

    private void paintSource(PNodeView nodeView) {
        setBackgroundColor(nodeView, getTheme().getSourceNodeColor());
    }

    private void paintBasic(PNodeView nodeView) {
        setBackgroundColor(nodeView, getTheme().getBasicNodeColor());
    }

    private void paintOpen(PNodeView nodeView) {
        setBackgroundColor(nodeView, getTheme().getOpenNodeColor());
    }

    private void paintClosed(PNodeView nodeView) {
        setBackgroundColor(nodeView, getTheme().getClosedNodeColor());
    }

    private void paintWall(PNodeView nodeView) {
        setBackgroundColor(nodeView, getTheme().getWallNodeColor());
    }

    private void paintPath(PNodeView nodeView) {
        setBackgroundColor(nodeView, getTheme().getPathNodeColor());
    }

    private void setBackgroundColor(PNodeView nodeView, Color color) {
        Background coloredBackground = new Background(new BackgroundFill(color, null, null));
        nodeView.setBackground(coloredBackground);
    }
}
