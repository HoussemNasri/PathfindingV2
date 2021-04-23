package tech.houssemnasri.toolbox;

import javafx.scene.Scene;

import tech.houssemnasri.mvp.Presenter;
import tech.houssemnasri.pathfinder.visualizer.Visualizer;
import tech.houssemnasri.pathfinder.factory.AlgorithmFactory;

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
