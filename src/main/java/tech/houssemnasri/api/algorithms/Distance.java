package tech.houssemnasri.api.algorithms;

import java.util.function.BiFunction;

import tech.houssemnasri.api.node.IPosition;

public abstract class Distance implements BiFunction<IPosition, IPosition, Integer> {
    protected final static int DEFAULT_FACTOR = 10;
    protected final int factor;

    public Distance(final int factor) {
        this.factor = factor;
    }

    public Distance() {
        this(DEFAULT_FACTOR);
    }
}
