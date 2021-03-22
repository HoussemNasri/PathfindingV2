package tech.houssemnasri.impl.pathfinder.distance;

import tech.houssemnasri.api.pathfinder.Distance;
import tech.houssemnasri.api.node.IPosition;
import static java.lang.Math.*;

public class ManhattanDistance extends Distance {

  public ManhattanDistance(final int factor) {
    super(factor);
  }

  public ManhattanDistance() {
    super();
  }

  @Override
  public Integer apply(IPosition a, IPosition b) {
    int dx = abs(a.getX() - b.getX());
    int dy = abs(a.getY() - b.getY());

    return factor * (dx + dy);
  }
}
