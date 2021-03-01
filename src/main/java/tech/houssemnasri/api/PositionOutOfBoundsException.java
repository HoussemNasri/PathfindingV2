package tech.houssemnasri.api;

import tech.houssemnasri.api.IPosition;
import tech.houssemnasri.impl.PPosition;

public class PositionOutOfBoundsException extends RuntimeException {
    public PositionOutOfBoundsException(IPosition position) {
        super(String.format("Position out of range: %s", position));
    }

    public PositionOutOfBoundsException(int x, int y) {
        this(PPosition.of(x, y));
    }
}
