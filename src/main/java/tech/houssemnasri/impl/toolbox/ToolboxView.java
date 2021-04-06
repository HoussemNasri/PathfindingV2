package tech.houssemnasri.impl.toolbox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import tech.houssemnasri.api.toolbox.IToolboxPresenter;
import tech.houssemnasri.api.toolbox.IToolboxView;

public class ToolboxView implements IToolboxView, Initializable {
  private FXMLLoader loader;
  @FXML private ComboBox<?> algorithmComboBox;

  @FXML private ComboBox<?> themeComboBox;

  @FXML private ToggleGroup wallEditorGroup;

  @FXML private Button resetButton;

  @FXML private Button playButton;

  @FXML private FontIcon playButtonIcon;

  @FXML private Button backButton;

  @FXML private Button forwardButton;

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
    playButton.setOnMouseClicked(e -> presenter.onPlayClicked());
    resetButton.setOnMouseClicked(e -> presenter.onResetPlayerClicked());
    forwardButton.setOnMouseClicked(e -> presenter.onForwardClicked());
    backButton.setOnMouseClicked(e -> presenter.onBackClicked());
  }

  @Override
  public void updatePlayButtonIcon(boolean isPlaying) {
    if (isPlaying) {
        playButtonIcon.setIconCode(FontAwesomeSolid.PAUSE);
    } else {
        playButtonIcon.setIconCode(FontAwesomeSolid.PLAY);
    }
  }
}
