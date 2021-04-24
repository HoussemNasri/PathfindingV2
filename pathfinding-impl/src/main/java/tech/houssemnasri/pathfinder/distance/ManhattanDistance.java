package tech.houssemnasri.pathfinder.distance;

import tech.houssemnasri.node.Position;
import tech.houssemnasri.pathfinder.Distance;

public class ManhattanDistance extends Distance {

  public ManhattanDistance(final int factor) {
    super(factor);
  }

  public ManhattanDistance() {
    super();
  }

  @Override
  public Integer apply(Position a, Position b) {
    int dx = Math.abs(a.getX() - b.getX());
    int dy = Math.abs(a.getY() - b.getY());

    return factor * (dx + dy);
  }
}
