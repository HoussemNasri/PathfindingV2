package tech.houssemnasri.impl.toolbox;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import tech.houssemnasri.api.toolbox.IToolbox;
import tech.houssemnasri.impl.AlgorithmDescriptor;
import tech.houssemnasri.impl.ThemeDescriptor;
import tech.houssemnasri.property.ComplexObjectProperty;

public class Toolbox implements IToolbox {
  private final ObjectProperty<AlgorithmDescriptor> selectedAlgorithmProperty =
      new ComplexObjectProperty<>();
  private final ObjectProperty<ThemeDescriptor> selectedThemeProperty =
      new ComplexObjectProperty<>();

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
}
