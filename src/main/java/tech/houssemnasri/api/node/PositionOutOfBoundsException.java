package tech.houssemnasri.api.node;

import tech.houssemnasri.impl.node.PPosition;

public class PositionOutOfBoundsException extends RuntimeException {
    public PositionOutOfBoundsException(IPosition position) {
        super(String.format("Position out of range: %s", position));
    }

    public PositionOutOfBoundsException(int x, int y) {
        this(PPosition.of(x, y));
    }
}
