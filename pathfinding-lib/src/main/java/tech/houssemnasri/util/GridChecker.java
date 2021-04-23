package tech.houssemnasri.util;

/**
 * This class consists of {@code static} utility methods for operating on grid, or checking certain
 * conditions before operation.
 */
public final class GridChecker {
  private GridChecker() {
    throw new AssertionError("No GridChecker instances for you!");
  }
  /**
   * Checks if the {@code position} is within the bounds of the range, {@code position.getX()} from
   * {@code 0} (inclusive) to {@code cols} (exclusive), {@code position.getY()} from {@code 0}
   * (inclusive) to {@code rows} (exclusive).
   *
   * <p>The {@code position} is defined to be out of bounds if any of the following inequalities is
   * true:
   *
   * <ul>
   *   <li>{@code position.getX() < 0}
   *   <li>{@code position.getY() < 0}
   *   <li>{@code position.getX() >= cols}
   *   <li>{@code position.getY() >= rows}
   * </ul>
   *
   * @param position the position
   * @param rows the number of rows in the grid
   * @param cols the number of columns in the grid
   * @return {@code True} if it is within bounds of the range, {@code False} otherwise.
   */
  public static boolean checkPosition(int x, int y, int rows, int cols) {
    return x >= 0
        && y >= 0
        && x < cols
        && y < rows;
  }
}
