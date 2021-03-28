package tech.houssemnasri;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import tech.houssemnasri.api.grid.IGrid;
import tech.houssemnasri.api.grid.IGridPresenter;
import tech.houssemnasri.api.grid.IGridView;
import tech.houssemnasri.api.pathfinder.BaseAlgorithmPlayer;
import tech.houssemnasri.api.toolbox.IToolbox;
import tech.houssemnasri.api.toolbox.IToolboxPresenter;
import tech.houssemnasri.api.toolbox.IToolboxView;
import tech.houssemnasri.impl.grid.PGrid;
import tech.houssemnasri.impl.grid.PGridPresenter;
import tech.houssemnasri.impl.grid.PGridView;
import tech.houssemnasri.impl.pathfinder.astar.AStarAlgorithm;
import tech.houssemnasri.impl.pathfinder.player.SimpleAlgoPlayer;
import tech.houssemnasri.impl.toolbox.Toolbox;
import tech.houssemnasri.impl.toolbox.ToolboxPresenter;
import tech.houssemnasri.impl.toolbox.ToolboxView;

public class App extends Application {
  private static int themeCounter = 0;

  @Override
  public void start(Stage primaryStage) {
    VBox root = new VBox();
    Scene scene = new Scene(root, 700, 500);
    scene.getStylesheets().add(getClass().getResource("/dracula.css").toString());

    IToolbox toolbox = new Toolbox();
    IToolboxView toolboxView = new ToolboxView();
    IToolboxPresenter toolboxPresenter = new ToolboxPresenter(toolbox, toolboxView);

    IGrid grid = new PGrid(45, 65);
    IGridView gridView = new PGridView();
    IGridPresenter gridPresenter = new PGridPresenter(grid, gridView);

    root.getChildren().add(toolboxView.getRoot());
    root.getChildren().add(gridView.getRoot());
    VBox.setVgrow(gridView.getRoot(), Priority.NEVER);

    BaseAlgorithmPlayer algorithmPlayer = new SimpleAlgoPlayer(new AStarAlgorithm(grid, true));
    listenForSceneClicks(gridPresenter, algorithmPlayer, scene);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void listenForSceneClicks(
      IGridPresenter gridPresenter, BaseAlgorithmPlayer algorithmPlayer, Scene scene) {
    scene.addEventFilter(
        MouseEvent.MOUSE_CLICKED,
        e -> {
          if (e.getButton() == MouseButton.MIDDLE) {
            scene.getStylesheets().add(getClass().getResource("/lighter.css").toString());
          } else if (e.getButton() == MouseButton.SECONDARY) {
            algorithmPlayer.play();
          }
        });
  }

  public static void main(String[] args) {
    launch(args);
  }
}
