package tech.houssemnasri.toolbox;

import java.util.Arrays;

import javafx.scene.Scene;

import tech.houssemnasri.pathfinder.visualizer.Visualizer;
import tech.houssemnasri.pathfinder.factory.AlgorithmFactory;
import tech.houssemnasri.pathfinder.factory.AlgorithmFactoryImpl;
import tech.houssemnasri.AlgorithmDescriptor;
import tech.houssemnasri.ThemeDescriptor;

public class ToolboxPresenter implements IToolboxPresenter, Visualizer.VisualizerListener {
  private Scene scene;
  private IToolboxView toolboxView;
  private IToolbox toolboxModel;
  private Visualizer visualizer;
  private AlgorithmFactory algorithmFactory;

  public ToolboxPresenter(
      Scene scene,
      IToolbox toolboxModel,
      IToolboxView toolboxView,
      AlgorithmFactoryImpl algorithmFactory,
      Visualizer visualizer) {
    setScene(scene);
    setToolboxModel(toolboxModel);
    setView(toolboxView);
    setAlgorithmFactory(algorithmFactory);
    setVisualizer(visualizer);
  }

  public ToolboxPresenter(
      Scene scene,
      IToolbox toolboxModel,
      IToolboxView toolboxView,
      AlgorithmFactoryImpl algorithmFactory) {
    this(scene, toolboxModel, toolboxView, algorithmFactory, null);
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
    handleThemeSelection();
  }

  @Override
  public void setVisualizer(Visualizer visualizer) {
    if (visualizer == null) return;
    this.visualizer = visualizer;
    visualizer.registerListener(this);
    toolboxModel.visualizationSpeedProperty().bind(visualizer.speedProperty());
    handleAlgorithmSelection();
  }

  @Override
  public void setScene(Scene scene) {
    if (scene == null) return;
    this.scene = scene;
  }

  @Override
  public void setAlgorithmFactory(AlgorithmFactory algorithmFactory) {
    if (algorithmFactory == null) return;
    this.algorithmFactory = algorithmFactory;
  }

  private void handleAlgorithmSelection() {
    toolboxModel
        .selectedAlgorithmProperty()
        .addListener(
            (obs, old, algorithm) -> {
              visualizer.setAlgorithm(algorithmFactory.getAlgorithm(algorithm));
            });
  }

  private void handleThemeSelection() {
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
    toolboxView.disableAlgorithmComboBox();
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
  public void onVisualizationStarted() {
    System.out.println("onStarted()");
    toolboxView.disableForwardButton();
    toolboxView.disableBackButton();
    toolboxView.disableAlgorithmComboBox();
  }

  @Override
  public void onVisualizationPaused() {
    System.out.println("onPaused()");
    toolboxView.enableForwardButton();
    toolboxView.enableBackButton();
  }

  @Override
  public void onVisualizationFinished() {
    System.out.println("onFinished()");
    toolboxView.updatePlayPauseButton(isPlaying());
    toolboxView.enableForwardButton();
    toolboxView.enableBackButton();
  }

  @Override
  public void onVisualizationReset() {
    System.out.println("onReset()");
    toolboxModel.unlockDraggingNodes();
    toolboxView.enableForwardButton();
    toolboxView.enableBackButton();
    toolboxView.enableAlgorithmComboBox();
  }
}
