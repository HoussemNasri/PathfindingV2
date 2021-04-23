package tech.houssemnasri;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import tech.houssemnasri.grid.IGrid;
import tech.houssemnasri.grid.IGridPresenter;
import tech.houssemnasri.grid.IGridView;
import tech.houssemnasri.grid.PGrid;
import tech.houssemnasri.grid.PGridPresenter;
import tech.houssemnasri.grid.PGridView;
import tech.houssemnasri.pathfinder.visualizer.Visualizer;
import tech.houssemnasri.pathfinder.factory.AlgorithmFactoryImpl;
import tech.houssemnasri.pathfinder.visualizer.SimpleVisualizer;
import tech.houssemnasri.toolbox.IToolbox;
import tech.houssemnasri.toolbox.IToolboxPresenter;
import tech.houssemnasri.toolbox.IToolboxView;
import tech.houssemnasri.toolbox.Toolbox;
import tech.houssemnasri.toolbox.ToolboxPresenter;
import tech.houssemnasri.toolbox.ToolboxView;

public class Launcher extends Application {
  private static final double SCENE_WIDTH = 950d;
  private static final double SCENE_HEIGHT = 650d;

  @Override
  public void start(Stage primaryStage) {
    VBox mainView = new VBox();
    Scene scene = new Scene(mainView, SCENE_WIDTH, SCENE_HEIGHT);

    IToolbox toolbox = new Toolbox();
    IGrid grid = new PGrid(35, 65);
    AlgorithmFactoryImpl algorithmFactory = new AlgorithmFactoryImpl(grid);

    IToolboxView toolboxView = new ToolboxView();
    IToolboxPresenter toolboxPresenter =
        new ToolboxPresenter(scene, toolbox, toolboxView, algorithmFactory);

    IGridView gridView = new PGridView();
    IGridPresenter gridPresenter = new PGridPresenter(grid, gridView, toolbox);

    mainView.getChildren().add(toolboxView.getRoot());
    mainView.getChildren().add(gridView.getRoot());

    Visualizer visualizer =
        new SimpleVisualizer(algorithmFactory.getAlgorithm(AlgorithmDescriptor.A_STAR));
    toolboxPresenter.setVisualizer(visualizer);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
