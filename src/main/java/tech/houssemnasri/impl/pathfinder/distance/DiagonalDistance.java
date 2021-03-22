package tech.houssemnasri.impl.pathfinder.distance;

import tech.houssemnasri.api.pathfinder.Distance;
import tech.houssemnasri.api.node.IPosition;
import static java.lang.Math.*;

public class DiagonalDistance extends Distance {
  private final int diagonalFactor;

  public DiagonalDistance(final int factor, final int diagonalFactor) {
    super(factor);
    this.diagonalFactor = diagonalFactor;
  }

  public DiagonalDistance(final int factor) {
    this(factor, (int) (factor * sqrt(2)));
  }

  public DiagonalDistance() {
    this(DEFAULT_FACTOR);
  }

  @Override
  public Integer apply(IPosition a, IPosition b) {
    int dx = abs(a.getX() - b.getX());
    int dy = abs(a.getY() - b.getY());

    return factor * (dx + dy) + (diagonalFactor - 2 * factor) * min(dx, dy);
  }
}
