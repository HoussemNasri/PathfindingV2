package tech.houssemnasri.pathfinder.distance;

import tech.houssemnasri.Position;
import tech.houssemnasri.pathfinder.Distance;

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
  public Integer apply(Position a, Position b) {
    int dx = abs(a.getX() - b.getX());
    int dy = abs(a.getY() - b.getY());

    return factor * (dx + dy) + (diagonalFactor - 2 * factor) * min(dx, dy);
  }
}
