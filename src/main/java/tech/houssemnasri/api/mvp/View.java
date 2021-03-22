package tech.houssemnasri.api.mvp;

import javafx.scene.layout.Region;

/** This interface must be implemented by simple view that don't need a presenter */
public interface View {
  /** Returns the JavaFx root node. */
  Region getRoot();
  /** Render the current state of the grid model on the screen. */
  void refresh();
}
