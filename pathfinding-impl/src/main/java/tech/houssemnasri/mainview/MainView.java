package tech.houssemnasri.mainview;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import tech.houssemnasri.grid.IGridView;
import tech.houssemnasri.mainview.IMainView;
import tech.houssemnasri.mainview.IMainViewPresenter;
import tech.houssemnasri.toolbox.IToolboxView;

public class MainView implements IMainView {
  public boolean isRefreshed = false;
  private VBox theRoot;

  @FXML private StackPane toolboxContainer;

  @FXML private StackPane gridContainer;

  @FXML private StackPane infoBarContainer;

  @Override
  public void setPresenter(IMainViewPresenter presenter) {}

  @Override
  public Pane getRoot() {
    return theRoot;
  }

  @Override
  public void refresh() {
    try {
      theRoot = FXMLLoader.load(getClass().getResource("/mainview.fxml"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // setGrid(gridView);
  }

  @Override
  public void setGrid(IGridView gridView) {
    if (gridContainer != null) {
      gridContainer.getChildren().setAll(gridView.getRoot());
    }
  }

  @Override
  public void setToolbox(IToolboxView toolbox) {}
}
