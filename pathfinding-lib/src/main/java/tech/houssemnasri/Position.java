package tech.houssemnasri;

import java.util.Objects;


/**
 * The {@code PPosition} class encapsulate the coordinate information of an {@code INode} while
 * providing some helper methods for comparison and cloning.
 */
public class Position {
  /** Indicates a wrong position */
  public static final Position ERROR = Position.of(-1, -1);

  private final int x;
  private final int y;

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Returns an {@code PPosition} of x and y coordinate.
   *
   * @param x the position on the x axis
   * @param y the position on the y axis
   * @return an {@code PPosition} with the x and y present
   */
  public static Position of(int x, int y) {
    return new Position(x, y);
  }

  /** Returns the x-coordinate */
  public int getX() {
    return x;
  }

  /** Returns the y-coordinate */
  public int getY() {
    return y;
  }

  @Override
  public String toString() {
    return String.format("(%d, %d)", x, y);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Position point = (Position) o;
    return x == point.x && y == point.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
