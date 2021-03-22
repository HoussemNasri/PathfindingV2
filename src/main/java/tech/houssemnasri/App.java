package tech.houssemnasri;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import tech.houssemnasri.api.grid.IGrid;
import tech.houssemnasri.api.grid.IGridPresenter;
import tech.houssemnasri.api.grid.IGridView;
import tech.houssemnasri.api.pathfinder.BaseAlgorithmPlayer;
import tech.houssemnasri.api.theme.ITheme;
import tech.houssemnasri.impl.animation.AnimationSuite;
import tech.houssemnasri.impl.grid.PGrid;
import tech.houssemnasri.impl.grid.PGridPresenter;
import tech.houssemnasri.impl.grid.PGridView;
import tech.houssemnasri.impl.pathfinder.astar.AStarAlgorithm;
import tech.houssemnasri.impl.pathfinder.player.SimpleAlgoPlayer;
import tech.houssemnasri.impl.theme.PTheme;

public class App extends Application {
  private static int themeCounter = 0;

  @Override
  public void start(Stage primaryStage) throws Exception {
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
    ITheme[] themes = {theme1, theme2, theme3};

    IGrid grid = PGrid.getInstance();
    IGridView gridView = PGridView.getInstance();
    IGridPresenter gridPresenter = new PGridPresenter(grid, gridView, theme1);
    Region realGridView = gridView.getUpdatedRoot();

    Group root = new Group(realGridView);

    Scene scene = new Scene(root, 700, 500);
    realGridView.prefWidthProperty().bind(scene.widthProperty());
    realGridView.prefHeightProperty().bind(scene.heightProperty());

    BaseAlgorithmPlayer algorithmPlayer = new SimpleAlgoPlayer(new AStarAlgorithm(grid, true));

    scene.addEventFilter(
        MouseEvent.MOUSE_CLICKED,
        e -> {
          if (e.getButton() == MouseButton.MIDDLE) {
            algorithmPlayer.back();
          } else if (e.getButton() == MouseButton.SECONDARY) {
            algorithmPlayer.play();
          }
        });

    primaryStage.setScene(scene);
    primaryStage.show();

    AnimationSuite suite1 = new AnimationSuite.Builder().build();
    AnimationSuite suite2 = new AnimationSuite.Builder().build();
    System.out.println(suite1.getOnEnterBasic());
    System.out.println(suite2.getOnEnterBasic());
  }


  public static void main(String[] args) {
    launch(args);
  }
}
