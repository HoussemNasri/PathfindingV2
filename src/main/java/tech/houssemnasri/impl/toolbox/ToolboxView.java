package tech.houssemnasri.impl.toolbox;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
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
import tech.houssemnasri.impl.AlgorithmDescriptor;
import tech.houssemnasri.impl.ThemeDescriptor;

public class ToolboxView implements IToolboxView, Initializable {
  private static final String PLAY_LABEL_TEXT = "Play";
  private static final String PAUSE_LABEL_TEXT = "Pause";

  private FXMLLoader loader;
  @FXML private ComboBox<String> algorithmComboBox;

  @FXML private ComboBox<String> themeComboBox;

  @FXML private ToggleGroup wallEditorGroup;

  @FXML private Button resetButton;

  @FXML private Button playPauseButton;

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
    playPauseButton.setOnMouseClicked(e -> presenter.onPlayClicked());
    resetButton.setOnMouseClicked(e -> presenter.onResetPlayerClicked());
    forwardButton.setOnMouseClicked(e -> presenter.onForwardClicked());
    backButton.setOnMouseClicked(e -> presenter.onBackClicked());
    listenForAlgorithmSelection();
    listenForThemeSelection();
    listenForWallDrawModeSelection();
  }

  private void listenForWallDrawModeSelection() {
    wallEditorGroup
        .selectedToggleProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              int selected = wallEditorGroup.getToggles().indexOf(newValue);
              presenter.onWallDrawModeSelected(selected);
            });
  }

  private void listenForAlgorithmSelection() {
    algorithmComboBox
        .getSelectionModel()
        .selectedIndexProperty()
        .addListener(
            (obs, old, index) -> {
              presenter.onAlgorithmSelected(index.intValue());
            });
  }

  private void listenForThemeSelection() {
    themeComboBox
        .getSelectionModel()
        .selectedIndexProperty()
        .addListener(
            (obs, old, index) -> {
              presenter.onThemeSelected(index.intValue());
            });
  }

  @Override
  public void updatePlayPauseButton(boolean isPlaying) {
    if (isPlaying) {
      playButtonIcon.setIconCode(FontAwesomeSolid.PAUSE);
      playPauseButton.setText(PAUSE_LABEL_TEXT);
    } else {
      playButtonIcon.setIconCode(FontAwesomeSolid.PLAY);
      playPauseButton.setText(PLAY_LABEL_TEXT);
    }
  }

  @Override
  public void putAlgorithms(AlgorithmDescriptor[] algorithms) {
    List<String> names =
        Arrays.stream(algorithms).map(AlgorithmDescriptor::getName).collect(Collectors.toList());
    algorithmComboBox.setItems(FXCollections.observableList(names));
  }

  @Override
  public void putThemes(ThemeDescriptor[] themes) {
    List<String> names =
        Arrays.stream(themes).map(ThemeDescriptor::getName).collect(Collectors.toList());
    themeComboBox.setItems(FXCollections.observableList(names));
  }
}
