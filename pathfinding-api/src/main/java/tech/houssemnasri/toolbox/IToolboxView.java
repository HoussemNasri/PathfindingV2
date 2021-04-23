package tech.houssemnasri.toolbox;

import javafx.scene.layout.Pane;

import tech.houssemnasri.AlgorithmDescriptor;
import tech.houssemnasri.ThemeDescriptor;
import tech.houssemnasri.mvp.PresentableView;

public interface IToolboxView extends PresentableView<IToolboxPresenter, Pane> {
  void updatePlayPauseButton(boolean isPlaying);

  void putAlgorithms(AlgorithmDescriptor[] algorithms);

  void putThemes(ThemeDescriptor[] themes);

  void disableForwardButton();

  void disableBackButton();

  void enableForwardButton();

  void enableBackButton();

  void disableAlgorithmComboBox();

  void enableAlgorithmComboBox();
}
