package tech.houssemnasri.api.toolbox;

import java.util.List;

import javafx.beans.property.ObjectProperty;

import tech.houssemnasri.impl.AlgorithmDescriptor;
import tech.houssemnasri.impl.ThemeDescriptor;

public interface IToolbox {
  enum WallDrawMode {
    DRAW_MODE,
    ERASE_MODE
  }

  void selectAlgorithm(AlgorithmDescriptor algorithm);

  AlgorithmDescriptor getSelectedAlgorithm();

  ObjectProperty<AlgorithmDescriptor> selectedAlgorithmProperty();

  void selectTheme(ThemeDescriptor theme);

  ThemeDescriptor getSelectedTheme();

  ObjectProperty<ThemeDescriptor> selectedThemeProperty();
}
