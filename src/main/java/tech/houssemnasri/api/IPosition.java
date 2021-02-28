package tech.houssemnasri.api;

/**
 * The {@code IPosition} is an immutable data structure responsible for holding the x and y's
 * position of a node in {@code IGrid}.
 */
public interface IPosition extends Cloneable {
    int getX();

    int getY();
}
