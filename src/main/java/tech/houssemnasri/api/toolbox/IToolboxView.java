package tech.houssemnasri.api.toolbox;

import javafx.scene.layout.Pane;

import org.kordamp.ikonli.javafx.FontIcon;
import tech.houssemnasri.api.mvp.PresentableView;

public interface IToolboxView extends PresentableView<IToolboxPresenter, Pane> {
  void updatePlayButtonIcon(boolean isPlaying);
}
