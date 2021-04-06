package tech.houssemnasri.impl.toolbox;

import tech.houssemnasri.api.pathfinder.BaseAlgorithmPlayer;
import tech.houssemnasri.api.pathfinder.OnPlayerFinishedListener;
import tech.houssemnasri.api.toolbox.IToolbox;
import tech.houssemnasri.api.toolbox.IToolboxPresenter;
import tech.houssemnasri.api.toolbox.IToolboxView;

public class ToolboxPresenter implements IToolboxPresenter, OnPlayerFinishedListener {
  private IToolboxView toolboxView;
  private IToolbox toolbox;
  private BaseAlgorithmPlayer algorithmPlayer;

  public ToolboxPresenter(
      IToolbox toolbox, IToolboxView toolboxView, BaseAlgorithmPlayer algorithmPlayer) {
    setToolbox(toolbox);
    setView(toolboxView);
    setAlgorithmPlayer(algorithmPlayer);
  }

  public ToolboxPresenter(IToolbox toolbox, IToolboxView toolboxView) {
    this(toolbox, toolboxView, null);
  }

  public ToolboxPresenter() {
    this(null, null);
  }

  @Override
  public void setView(IToolboxView view) {
    if (view == null) return;
    this.toolboxView = view;
    this.toolboxView.setPresenter(this);
  }

  @Override
  public void setToolbox(IToolbox toolbox) {
    if (toolbox == null) return;
    this.toolbox = toolbox;
  }

  @Override
  public void setAlgorithmPlayer(BaseAlgorithmPlayer algorithmPlayer) {
    if (algorithmPlayer == null) return;
    this.algorithmPlayer = algorithmPlayer;
    algorithmPlayer.registerOnFinishedListener(this);
  }

  @Override
  public void onPlayClicked() {
    if (isPlaying()) {
      algorithmPlayer.pause();
    } else {
      algorithmPlayer.play();
    }
    toolboxView.updatePlayButtonIcon(isPlaying());
  }

  private boolean isPlaying() {
    return algorithmPlayer.getStatus() == BaseAlgorithmPlayer.Status.RUNNING;
  }

  @Override
  public void onForwardClicked() {}

  @Override
  public void onBackClicked() {}

  @Override
  public void onAlgorithmSelected(int algorithmIndex) {}

  @Override
  public void onThemeSelected(int themeIndex) {}

  @Override
  public void onResetPlayerClicked() {
    algorithmPlayer.reset();
    toolboxView.updatePlayButtonIcon(isPlaying());
  }

  @Override
  public void onFinished() {
    toolboxView.updatePlayButtonIcon(isPlaying());
  }
}
