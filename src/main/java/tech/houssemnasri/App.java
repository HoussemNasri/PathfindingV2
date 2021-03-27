package tech.houssemnasri;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import tech.houssemnasri.api.grid.IGrid;
import tech.houssemnasri.api.grid.IGridPresenter;
import tech.houssemnasri.api.grid.IGridView;
import tech.houssemnasri.api.pathfinder.BaseAlgorithmPlayer;
import tech.houssemnasri.api.theme.ITheme;
import tech.houssemnasri.api.toolbox.IToolbox;
import tech.houssemnasri.api.toolbox.IToolboxPresenter;
import tech.houssemnasri.api.toolbox.IToolboxView;
import tech.houssemnasri.impl.grid.PGrid;
import tech.houssemnasri.impl.grid.PGridPresenter;
import tech.houssemnasri.impl.grid.PGridView;
import tech.houssemnasri.impl.pathfinder.astar.AStarAlgorithm;
import tech.houssemnasri.impl.pathfinder.player.SimpleAlgoPlayer;
import tech.houssemnasri.impl.theme.PTheme;
import tech.houssemnasri.impl.toolbox.Toolbox;
import tech.houssemnasri.impl.toolbox.ToolboxPresenter;
import tech.houssemnasri.impl.toolbox.ToolboxView;

public class App extends Application {
  private static int themeCounter = 0;
  private ITheme[] themes;

  private void initThemes() {
    ITheme theme1 = new PTheme.Builder().build();
    ITheme theme2 =
        new PTheme.Builder()
            .setSourceNodeColor(Color.LIGHTSLATEGREY)
            .setWallNodeColor(Color.NAVAJOWHITE)
            .setBasicNodeColor(Color.SPRINGGREEN)
            .setDestinationNodeColor(Color.ROYALBLUE)
            .build();

    ITheme theme3 =
        new PTheme.Builder()
            .setSourceNodeColor(Color.DODGERBLUE)
            .setWallNodeColor(Color.DARKOLIVEGREEN)
            .setBasicNodeColor(Color.CORAL)
            .setDestinationNodeColor(Color.DARKORCHID)
            .build();
    themes = new ITheme[] {theme1, theme2, theme3};
  }

  @Override
  public void start(Stage primaryStage) {
    initThemes();
    VBox root = new VBox();
    Scene scene = new Scene(root, 700, 500);

    IToolbox toolbox = new Toolbox();
    IToolboxView toolboxView = new ToolboxView();
    IToolboxPresenter toolboxPresenter = new ToolboxPresenter(toolbox, toolboxView);

    IGrid grid = new PGrid(40, 60);
    IGridView gridView = new PGridView();
    IGridPresenter gridPresenter = new PGridPresenter(grid, gridView);

    root.getChildren().add(toolboxView.getRoot());
    root.getChildren().add(gridView.getRoot());

    BaseAlgorithmPlayer algorithmPlayer = new SimpleAlgoPlayer(new AStarAlgorithm(grid, true));
    listenForSceneClicks(themes, gridPresenter, algorithmPlayer, scene);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void listenForSceneClicks(
      ITheme[] themes,
      IGridPresenter gridPresenter,
      BaseAlgorithmPlayer algorithmPlayer,
      Scene scene) {
    scene.addEventFilter(
        MouseEvent.MOUSE_CLICKED,
        e -> {
          if (e.getButton() == MouseButton.MIDDLE) {
            gridPresenter.setShowCostInfo(++themeCounter % 2 == 0);
            // gridPresenter.setTheme(themes[++themeCounter % themes.length]);
          } else if (e.getButton() == MouseButton.SECONDARY) {
            // algorithmPlayer.play();
          }
        });
  }

  public static void main(String[] args) {
    launch(args);
  }
}
