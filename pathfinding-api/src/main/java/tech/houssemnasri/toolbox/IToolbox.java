package tech.houssemnasri.toolbox;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;

import tech.houssemnasri.AlgorithmDescriptor;
import tech.houssemnasri.ThemeDescriptor;

public interface IToolbox {
  enum WallDrawMode {
    DRAW,
    ERASE
  }

  void selectAlgorithm(AlgorithmDescriptor algorithm);

  AlgorithmDescriptor getSelectedAlgorithm();

  ObjectProperty<AlgorithmDescriptor> selectedAlgorithmProperty();

  void selectTheme(ThemeDescriptor theme);

  ThemeDescriptor getSelectedTheme();

  ObjectProperty<ThemeDescriptor> selectedThemeProperty();

  void setWallDrawMode(WallDrawMode mode);

  WallDrawMode getWallDrawMode();

  ObjectProperty<WallDrawMode> wallDrawModeProperty();

  void lockDraggingNodes();

  void unlockDraggingNodes();

  boolean isDraggingNodesLocked();

  BooleanProperty isLockedDraggingNodesProperty();

  void setVisualizationSpeed(double speed);

  double getVisualizationSpeed();

  DoubleProperty visualizationSpeedProperty();
}
