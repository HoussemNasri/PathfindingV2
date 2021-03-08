package tech.houssemnasri.api.theme;

import javafx.scene.paint.Color;

public interface ITheme {
    public Color getWallNodeColor();

    public Color getOpenNodeColor();

    public Color getClosedNodeColor();

    public Color getBasicNodeColor();

    public Color getPathNodeColor();

    public Color getSourceNodeColor();

    public Color getDestinationNodeColor();
}
