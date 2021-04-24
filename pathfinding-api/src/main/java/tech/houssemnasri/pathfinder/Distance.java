package tech.houssemnasri.pathfinder;

import java.util.function.BiFunction;

import tech.houssemnasri.node.Position;

public abstract class Distance implements BiFunction<Position, Position, Integer> {
  protected static final int DEFAULT_FACTOR = 10;
  protected final int factor;

  public Distance(final int factor) {
    this.factor = factor;
  }

  public Distance() {
    this(DEFAULT_FACTOR);
  }
}
