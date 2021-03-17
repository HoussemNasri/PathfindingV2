package tech.houssemnasri.impl.node;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import tech.houssemnasri.api.theme.ITheme;

import static tech.houssemnasri.api.node.INode.*;

/** This class{@code NodePainter} is responsible for painting the view according to it's type and the current theme. */
public class NodePainter {
    private final Pane toPaintView;
    private ITheme theme;
    private Type currentType = null;

    public NodePainter(Pane toPaintView, ITheme theme) {
        this.toPaintView = toPaintView;
        this.theme = theme;
    }

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
        this.currentType = nodeType;
    }

    public void switchTheme(ITheme newTheme){
        this.theme = newTheme;
        if(currentType != null){
            paint(currentType);
        }
    }

    private void paintDestination() {
        setBackgroundColor(getTheme().getDestinationNodeColor());
    }

    private void paintSource() {
        setBackgroundColor(getTheme().getSourceNodeColor());
    }

    private void paintBasic() {
        setBackgroundColor(getTheme().getBasicNodeColor());
    }

    private void paintOpen() {
        setBackgroundColor(getTheme().getOpenNodeColor());
    }

    private void paintClosed() {
        setBackgroundColor(getTheme().getClosedNodeColor());
    }

    private void paintWall() {
        setBackgroundColor(getTheme().getWallNodeColor());
    }

    private void paintPath() {
        setBackgroundColor(getTheme().getPathNodeColor());
    }

    private void setBackgroundColor(Color color) {
        Background coloredBackground = new Background(new BackgroundFill(color, null, null));
        toPaintView.setBackground(coloredBackground);
    }

    public ITheme getTheme() {
        return theme;
    }
}
