package tech.houssemnasri.impl.toolbox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import tech.houssemnasri.api.toolbox.IToolboxPresenter;
import tech.houssemnasri.api.toolbox.IToolboxView;

public class ToolboxView implements IToolboxView, Initializable {
  private FXMLLoader loader;

  @FXML private AnchorPane titlePane;

  @FXML private AnchorPane toolPane;

  @FXML private ComboBox<?> algorithmComboBox;

  private IToolboxPresenter presenter;

  public ToolboxView(IToolboxPresenter presenter) {
    setPresenter(presenter);
  }

  public ToolboxView() {
    this(null);
  }

  @Override
  public void setPresenter(IToolboxPresenter presenter) {
    if (presenter == null) return;
    this.presenter = presenter;
    refresh();
  }

  @Override
  public Pane getRoot() {
    return loader.getRoot();
  }

  @Override
  public void refresh() {
    try {
      loader = new FXMLLoader(getClass().getResource("/toolbox.fxml"));
      loader.setController(this);
      loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    System.out.println("ToolboxView.initialize()");
  }
}
