package tech.houssemnasri.api.toolbox;

import javafx.scene.Scene;

import tech.houssemnasri.api.mvp.Presenter;
import tech.houssemnasri.api.pathfinder.Visualizer;

public interface IToolboxPresenter extends Presenter<IToolboxView> {
  void setToolboxModel(IToolbox toolboxModel);

  void setVisualizer(Visualizer visualizer);

  void setScene(Scene scene);

  void onPlayClicked();

  void onForwardClicked();

  void onBackClicked();

  void onResetPlayerClicked();

  void onAlgorithmSelected(int algorithmIndex);

  void onThemeSelected(int themeIndex);

  void onWallDrawModeSelected(int selected);
}
