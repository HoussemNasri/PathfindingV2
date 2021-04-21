package tech.houssemnasri;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import tech.houssemnasri.api.grid.IGrid;
import tech.houssemnasri.api.grid.IGridPresenter;
import tech.houssemnasri.api.grid.IGridView;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.pathfinder.Visualizer;
import tech.houssemnasri.api.toolbox.IToolbox;
import tech.houssemnasri.api.toolbox.IToolboxPresenter;
import tech.houssemnasri.api.toolbox.IToolboxView;
import tech.houssemnasri.impl.AlgorithmDescriptor;
import tech.houssemnasri.impl.grid.PGrid;
import tech.houssemnasri.impl.grid.PGridPresenter;
import tech.houssemnasri.impl.grid.PGridView;
import tech.houssemnasri.impl.node.PNode;
import tech.houssemnasri.impl.node.Position;
import tech.houssemnasri.impl.node.experiment.ExprNodeView;
import tech.houssemnasri.impl.pathfinder.factory.AlgorithmFactory;
import tech.houssemnasri.impl.pathfinder.visualizer.SimpleVisualizer;
import tech.houssemnasri.impl.toolbox.Toolbox;
import tech.houssemnasri.impl.toolbox.ToolboxPresenter;
import tech.houssemnasri.impl.toolbox.ToolboxView;

public class App extends Application {
  private static final double SCENE_WIDTH = 950d;
  private static final double SCENE_HEIGHT = 650d;

  @Override
  public void start(Stage primaryStage) {
    VBox mainView = new VBox();
    Scene scene = new Scene(mainView, SCENE_WIDTH, SCENE_HEIGHT);

    IToolbox toolbox = new Toolbox();
    IGrid grid = new PGrid(45, 65);
    AlgorithmFactory algorithmFactory = new AlgorithmFactory(grid);

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

    Group testRoot = new Group();
    Scene testScene = new Scene(testRoot, 500, 500);
    testRoot.getStylesheets().add(getClass().getResource("/theme/dracula.css").toExternalForm());
    PNode node = new PNode(Position.of(-1, -1));
    ExprNodeView nodeView = new ExprNodeView(node);
    nodeView.setLayoutX(20);
    nodeView.setLayoutY(20);
    testRoot.getChildren().add(nodeView);
    nodeView.addEventFilter(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) event -> {
        int size = INode.Type.values().length;
        node.setType(INode.Type.values()[(node.getType().ordinal() + 1) % size]);
    });

    primaryStage.setScene(testScene);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
