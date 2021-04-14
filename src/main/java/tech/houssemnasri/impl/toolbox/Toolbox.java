package tech.houssemnasri.impl.toolbox;

import javafx.beans.property.ObjectProperty;
import tech.houssemnasri.api.toolbox.IToolbox;
import tech.houssemnasri.impl.AlgorithmDescriptor;
import tech.houssemnasri.impl.ThemeDescriptor;
import tech.houssemnasri.property.ComplexObjectProperty;

public class Toolbox implements IToolbox {
  private final ObjectProperty<AlgorithmDescriptor> selectedAlgorithmProperty =
      new ComplexObjectProperty<>();
  private final ObjectProperty<ThemeDescriptor> selectedThemeProperty =
      new ComplexObjectProperty<>();
  private final ObjectProperty<WallDrawMode> wallDrawModeProperty =
      new ComplexObjectProperty<>(WallDrawMode.DRAW);

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
}
