package tech.houssemnasri.mvp;

/**
 * This interface must be implemented by simple view that don't need a presenter
 *
 * @param <V> the view type
 */
public interface View<V> {
  /** Returns the JavaFx root node. */
  V getRoot();
  /** Render the current state of the grid model on the screen. */
  void refresh();
}
