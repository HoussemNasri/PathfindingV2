package tech.houssemnasri.impl;

import java.util.Objects;

import tech.houssemnasri.api.IPosition;

public class PPosition implements IPosition {
    private final int x;
    private final int y;

    public PPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static PPosition of(int x, int y) {
        return new PPosition(x, y);
    }

    @Override
    public int getX() {
        return x;
    }

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
