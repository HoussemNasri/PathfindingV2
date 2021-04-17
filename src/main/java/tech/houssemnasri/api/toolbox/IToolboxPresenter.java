package tech.houssemnasri.api.toolbox;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;

import tech.houssemnasri.api.mvp.Presenter;
import tech.houssemnasri.api.pathfinder.Visualizer;
import tech.houssemnasri.impl.pathfinder.factory.AlgorithmFactory;

public interface IToolboxPresenter extends Presenter<IToolboxView> {
  void setToolboxModel(IToolbox toolboxModel);

  void setVisualizer(Visualizer visualizer);

  void setScene(Scene scene);

  void setAlgorithmFactory(AlgorithmFactory algorithmFactory);

  void onPlayClicked();

  void onForwardClicked();

  void onBackClicked();

  void onResetPlayerClicked();

  void onAlgorithmSelected(int algorithmIndex);

  void onThemeSelected(int themeIndex);

  void onWallDrawModeSelected(int selected);

  void onSpeedSliderValueChanged(double newValue);
}
