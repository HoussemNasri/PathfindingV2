package tech.houssemnasri.impl.theme;

import javafx.scene.paint.Color;

import tech.houssemnasri.api.theme.ITheme;

/**
 * The {@code PTheme} class is a data structure responsible for holding theme configurations e.g.
 * colors, fonts.., clients can use these info to decide how to display views.
 */
public class PTheme implements ITheme {
  private Color colorWallNode = Color.valueOf("#222021");
  private Color colorOpenNode = Color.MEDIUMSEAGREEN;
  private Color colorClosedNode = Color.INDIANRED;
  private Color colorBasicNode = Color.IVORY;
  private Color colorPathNode = Color.GOLD;
  private Color colorSourceNode = Color.INDIGO;
  private Color colorDestinationNode = Color.DEEPSKYBLUE;

  private PTheme() {}

  @Override
  public Color getWallNodeColor() {
    return colorWallNode;
  }

  @Override
  public Color getOpenNodeColor() {
    return colorOpenNode;
  }

  @Override
  public Color getClosedNodeColor() {
    return colorClosedNode;
  }

  @Override
  public Color getBasicNodeColor() {
    return colorBasicNode;
  }

  @Override
  public Color getPathNodeColor() {
    return colorPathNode;
  }

  @Override
  public Color getSourceNodeColor() {
    return colorSourceNode;
  }

  @Override
  public Color getDestinationNodeColor() {
    return colorDestinationNode;
  }

  public static class Builder {
    private final PTheme instance = new PTheme();

    public Builder setWallNodeColor(Color color) {
      instance.colorWallNode = color;
      return this;
    }

    public Builder setBasicNodeColor(Color color) {
      instance.colorBasicNode = color;
      return this;
    }

    public Builder setSourceNodeColor(Color color) {
      instance.colorSourceNode = color;
      return this;
    }

    public Builder setDestinationNodeColor(Color color) {
      instance.colorDestinationNode = color;
      return this;
    }

    public Builder setOpenNodeColor(Color color) {
      instance.colorOpenNode = color;
      return this;
    }

    public Builder setClosedNodeColor(Color color) {
      instance.colorClosedNode = color;
      return this;
    }

    public Builder setPathNodeColor(Color color) {
      instance.colorPathNode = color;
      return this;
    }

    public ITheme build() {
      return instance;
    }
  }

  enum Font {}
}
