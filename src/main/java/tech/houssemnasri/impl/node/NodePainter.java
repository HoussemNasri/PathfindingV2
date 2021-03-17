package tech.houssemnasri.impl.node;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import tech.houssemnasri.api.theme.ITheme;

import static tech.houssemnasri.api.node.INode.*;

/** This class{@code NodePainter} is responsible for painting the view according to it's type and the current theme. */
public class NodePainter {
    private final ObjectProperty<ITheme> themeProperty = new SimpleObjectProperty<>();

    public NodePainter(ITheme theme) {
        switchTheme(theme);
    }

    public void paint(Pane nodeView, Type nodeType) {
        //At startup we might not have the theme ready so we need to wait for
        // the presenter to send us the theme, and execute the theming code at {code changeTheme()}
        if(getTheme() == null) return;
        switch (nodeType){
            case BASIC -> paintBasic(nodeView);
            case OPEN -> paintOpen(nodeView);
            case CLOSED -> paintClosed(nodeView);
            case WALL -> paintWall(nodeView);
            case PATH -> paintPath(nodeView);
            case SOURCE -> paintSource(nodeView);
            case DESTINATION -> paintDestination(nodeView);
        }
    }

    public void switchTheme(ITheme newTheme){
        themeProperty.set(newTheme);
    }

    private void paintDestination(Pane nodeView) {
        setBackgroundColor(nodeView, getTheme().getDestinationNodeColor());
    }

    private void paintSource(Pane nodeView) {
        setBackgroundColor(nodeView, getTheme().getSourceNodeColor());
    }

    private void paintBasic(Pane nodeView) {
        setBackgroundColor(nodeView, getTheme().getBasicNodeColor());
    }

    private void paintOpen(Pane nodeView) {
        setBackgroundColor(nodeView, getTheme().getOpenNodeColor());
    }

    private void paintClosed(Pane nodeView) {
        setBackgroundColor(nodeView, getTheme().getClosedNodeColor());
    }

    private void paintWall(Pane nodeView) {
        setBackgroundColor(nodeView, getTheme().getWallNodeColor());
    }

    private void paintPath(Pane nodeView) {
        setBackgroundColor(nodeView, getTheme().getPathNodeColor());
    }

    private void setBackgroundColor(Pane nodeView, Color color) {
        Background coloredBackground = new Background(new BackgroundFill(color, null, null));
        nodeView.setBackground(coloredBackground);
    }

    public ITheme getTheme() {
        return themeProperty.get();
    }

    public ObjectProperty<ITheme> themeProperty() {
        return themeProperty;
    }
}
