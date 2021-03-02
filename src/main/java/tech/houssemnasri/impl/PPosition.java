package tech.houssemnasri.impl;

import java.util.Objects;

import tech.houssemnasri.api.IPosition;

/**
 * The {@code PPosition} class encapsulate the coordinate information of an {@code INode} while
 * providing some helper methods for comparison and cloning.
 */
public class PPosition implements IPosition {
    /** Indicates a wrong position */
    public static final PPosition ERROR = PPosition.of(-1, -1);

    private final int x;
    private final int y;

    public PPosition(int x, int y) {
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
    public static PPosition of(int x, int y) {
        return new PPosition(x, y);
    }

    /** Returns the x-coordinate */
    @Override
    public int getX() {
        return x;
    }

    /** Returns the y-coordinate */
    @Override
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
        PPosition point = (PPosition) o;
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
