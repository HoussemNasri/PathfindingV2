package tech.houssemnasri.impl.toolbox;

import tech.houssemnasri.api.pathfinder.Visualizer;
import tech.houssemnasri.api.toolbox.IToolbox;
import tech.houssemnasri.api.toolbox.IToolboxPresenter;
import tech.houssemnasri.api.toolbox.IToolboxView;

public class ToolboxPresenter implements IToolboxPresenter, Visualizer.OnFinishListener {
  private IToolboxView toolboxView;
  private IToolbox toolboxModel;
  private Visualizer algorithmPlayer;

  public ToolboxPresenter(IToolbox toolboxModel, IToolboxView toolboxView, Visualizer algorithmPlayer) {
    setToolboxModel(toolboxModel);
    setView(toolboxView);
    setAlgorithmPlayer(algorithmPlayer);
  }

  public ToolboxPresenter(IToolbox toolboxModel, IToolboxView toolboxView) {
    this(toolboxModel, toolboxView, null);
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
  public void setToolboxModel(IToolbox toolboxModel) {
    if (toolboxModel == null) return;
    this.toolboxModel = toolboxModel;
  }

  @Override
  public void setAlgorithmPlayer(Visualizer algorithmPlayer) {
    if (algorithmPlayer == null) return;
    this.algorithmPlayer = algorithmPlayer;
    algorithmPlayer.registerFinishListener(this);
  }

  @Override
  public void onPlayClicked() {
    if (isPlaying()) {
      algorithmPlayer.pause();
    } else {
      algorithmPlayer.visualize();
    }
    toolboxView.updatePlayButtonIcon(isPlaying());
  }

  private boolean isPlaying() {
    return algorithmPlayer.getStatus() == Visualizer.Status.RUNNING;
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
  public void onFinish() {
    toolboxView.updatePlayButtonIcon(isPlaying());
  }
}
