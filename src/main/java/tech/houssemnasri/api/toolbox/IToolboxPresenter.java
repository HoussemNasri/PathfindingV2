package tech.houssemnasri.api.toolbox;

import tech.houssemnasri.api.mvp.Presenter;
import tech.houssemnasri.api.pathfinder.Visualizer;

public interface IToolboxPresenter extends Presenter<IToolboxView> {
  void setToolboxModel(IToolbox toolboxModel);

  void setAlgorithmPlayer(Visualizer algorithmPlayer);

  void onPlayClicked();

  void onForwardClicked();

  void onBackClicked();

  void onResetPlayerClicked();

  void onAlgorithmSelected(int algorithmIndex);

  void onThemeSelected(int themeIndex);
}
