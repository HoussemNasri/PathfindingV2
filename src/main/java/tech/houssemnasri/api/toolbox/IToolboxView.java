package tech.houssemnasri.api.toolbox;

import javafx.scene.layout.Pane;

import org.kordamp.ikonli.javafx.FontIcon;
import tech.houssemnasri.api.mvp.PresentableView;
import tech.houssemnasri.impl.AlgorithmDescriptor;
import tech.houssemnasri.impl.ThemeDescriptor;

public interface IToolboxView extends PresentableView<IToolboxPresenter, Pane> {
  void updatePlayPauseButton(boolean isPlaying);
  void putAlgorithms(AlgorithmDescriptor[] algorithms);
  void putThemes(ThemeDescriptor[] themes);
}
