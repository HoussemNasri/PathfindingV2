package tech.houssemnasri.api.toolbox;

import tech.houssemnasri.api.mvp.Presenter;
import tech.houssemnasri.api.pathfinder.BaseAlgorithmPlayer;

public interface IToolboxPresenter extends Presenter<IToolboxView> {
  void setToolbox(IToolbox toolbox);

  void setAlgorithmPlayer(BaseAlgorithmPlayer algorithmPlayer);

  void onPlayClicked();

  void onForwardClicked();

  void onBackClicked();

  void onSelectAlgorithm(int algorithmIndex);


}
