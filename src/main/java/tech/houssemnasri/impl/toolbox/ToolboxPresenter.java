package tech.houssemnasri.impl.toolbox;

import java.util.Arrays;

import javafx.scene.Scene;

import tech.houssemnasri.api.pathfinder.Visualizer;
import tech.houssemnasri.api.toolbox.IToolbox;
import tech.houssemnasri.api.toolbox.IToolboxPresenter;
import tech.houssemnasri.api.toolbox.IToolboxView;
import tech.houssemnasri.impl.AlgorithmDescriptor;
import tech.houssemnasri.impl.ThemeDescriptor;

public class ToolboxPresenter implements IToolboxPresenter, Visualizer.VisualizerListener {
  private Scene scene;
  private IToolboxView toolboxView;
  private IToolbox toolboxModel;
  private Visualizer visualizer;

  public ToolboxPresenter(
      Scene scene, IToolbox toolboxModel, IToolboxView toolboxView, Visualizer visualizer) {
    setScene(scene);
    setToolboxModel(toolboxModel);
    setView(toolboxView);
    setVisualizer(visualizer);
  }

  public ToolboxPresenter(Scene scene, IToolbox toolboxModel, IToolboxView toolboxView) {
    this(scene, toolboxModel, toolboxView, null);
  }

  public ToolboxPresenter(Scene scene) {
    this(scene, new Toolbox(), new ToolboxView());
  }

  @Override
  public void setView(IToolboxView view) {
    if (view == null) return;
    this.toolboxView = view;
    this.toolboxView.setPresenter(this);
    this.toolboxView.putAlgorithms(AlgorithmDescriptor.values());
    this.toolboxView.putThemes(ThemeDescriptor.values());
  }

  @Override
  public void setToolboxModel(IToolbox toolboxModel) {
    if (toolboxModel == null) return;
    this.toolboxModel = toolboxModel;
    listenForThemeChange();
  }

  @Override
  public void setVisualizer(Visualizer visualizer) {
    if (visualizer == null) return;
    this.visualizer = visualizer;
    visualizer.registerFinishListener(this);
    toolboxModel.visualizationSpeedProperty().bind(visualizer.speedProperty());
  }

  @Override
  public void setScene(Scene scene) {
    if (scene == null) return;
    this.scene = scene;
  }

  private void listenForThemeChange() {
    toolboxModel
        .selectedThemeProperty()
        .addListener(
            (obs, old, theme) -> {
              // Removing previous stylesheets.
              Arrays.stream(ThemeDescriptor.values())
                  .map(ThemeDescriptor::getPath)
                  .forEach(scene.getStylesheets()::remove);
              scene.getStylesheets().add(theme.getPath());
            });
  }

  @Override
  public void onPlayClicked() {
    if (isPlaying()) {
      visualizer.pause();
    } else {
      visualizer.visualize();
    }
    toolboxModel.lockDraggingNodes();
    toolboxView.updatePlayPauseButton(isPlaying());
  }

  private boolean isPlaying() {
    return visualizer.getStatus() == Visualizer.Status.RUNNING;
  }

  @Override
  public void onForwardClicked() {
    toolboxModel.lockDraggingNodes();
    visualizer.forward();
  }

  @Override
  public void onBackClicked() {
    visualizer.back();
  }

  @Override
  public void onAlgorithmSelected(int algorithmIndex) {
    toolboxModel.selectAlgorithm(AlgorithmDescriptor.values()[algorithmIndex]);
  }

  @Override
  public void onThemeSelected(int themeIndex) {
    toolboxModel.selectTheme(ThemeDescriptor.values()[themeIndex]);
  }

  @Override
  public void onWallDrawModeSelected(int selected) {
    toolboxModel.setWallDrawMode(
        selected == 0 ? IToolbox.WallDrawMode.DRAW : IToolbox.WallDrawMode.ERASE);
  }

  @Override
  public void onSpeedSliderValueChanged(double newValue) {
    visualizer.setSpeed(newValue);
  }

  @Override
  public void onResetPlayerClicked() {
    visualizer.reset();
    toolboxView.updatePlayPauseButton(isPlaying());
  }

  @Override
  public void onFinish() {
    toolboxView.updatePlayPauseButton(isPlaying());
    System.out.println("onFinish()");
  }

  @Override
  public void onReset() {
    toolboxModel.unlockDraggingNodes();
    System.out.println("onReset()");
  }
}
