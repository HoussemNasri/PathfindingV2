package tech.houssemnasri.toolbox;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

import tech.houssemnasri.toolbox.IToolbox;
import tech.houssemnasri.AlgorithmDescriptor;
import tech.houssemnasri.ThemeDescriptor;
import tech.houssemnasri.property.ComplexObjectProperty;

public class Toolbox implements IToolbox {
  private final ObjectProperty<AlgorithmDescriptor> selectedAlgorithmProperty =
      new ComplexObjectProperty<>();
  private final ObjectProperty<ThemeDescriptor> selectedThemeProperty =
      new ComplexObjectProperty<>();
  private final ObjectProperty<WallDrawMode> wallDrawModeProperty =
      new ComplexObjectProperty<>(WallDrawMode.DRAW);
  private final BooleanProperty isDraggingNodesLockedProperty = new SimpleBooleanProperty(false);
  private final DoubleProperty visualizationSpeedProperty = new SimpleDoubleProperty(5d);

  public Toolbox(AlgorithmDescriptor selectAlgorithm, ThemeDescriptor selectTheme) {
    selectAlgorithm(selectAlgorithm);
    selectTheme(selectTheme);
  }

  public Toolbox() {
    this(AlgorithmDescriptor.A_STAR, ThemeDescriptor.DRACULA);
  }

  @Override
  public void selectAlgorithm(AlgorithmDescriptor algorithm) {
    selectedAlgorithmProperty.set(algorithm);
  }

  @Override
  public AlgorithmDescriptor getSelectedAlgorithm() {
    return selectedAlgorithmProperty.get();
  }

  @Override
  public ObjectProperty<AlgorithmDescriptor> selectedAlgorithmProperty() {
    return selectedAlgorithmProperty;
  }

  @Override
  public void selectTheme(ThemeDescriptor theme) {
    selectedThemeProperty.set(theme);
  }

  @Override
  public ThemeDescriptor getSelectedTheme() {
    return selectedThemeProperty.get();
  }

  @Override
  public ObjectProperty<ThemeDescriptor> selectedThemeProperty() {
    return selectedThemeProperty;
  }

  @Override
  public void setWallDrawMode(WallDrawMode mode) {
    wallDrawModeProperty.set(mode);
  }

  @Override
  public WallDrawMode getWallDrawMode() {
    return wallDrawModeProperty.get();
  }

  @Override
  public ObjectProperty<WallDrawMode> wallDrawModeProperty() {
    return wallDrawModeProperty;
  }

  @Override
  public void lockDraggingNodes() {
    isDraggingNodesLockedProperty.set(true);
  }

  @Override
  public void unlockDraggingNodes() {
    isDraggingNodesLockedProperty.set(false);
  }

  @Override
  public boolean isDraggingNodesLocked() {
    return isDraggingNodesLockedProperty.get();
  }

  @Override
  public BooleanProperty isLockedDraggingNodesProperty() {
    return isDraggingNodesLockedProperty;
  }

  @Override
  public void setVisualizationSpeed(double speed) {
    visualizationSpeedProperty.set(speed);
  }

  @Override
  public double getVisualizationSpeed() {
    return visualizationSpeedProperty.get();
  }

  @Override
  public DoubleProperty visualizationSpeedProperty() {
    return visualizationSpeedProperty;
  }
}
