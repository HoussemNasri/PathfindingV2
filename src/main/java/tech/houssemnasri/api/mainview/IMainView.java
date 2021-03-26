package tech.houssemnasri.api.mainview;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import tech.houssemnasri.api.grid.IGridView;
import tech.houssemnasri.api.mvp.PresentableView;
import tech.houssemnasri.api.toolbox.IToolboxView;

public interface IMainView extends PresentableView<IMainViewPresenter, Pane>, Initializable {
  void setGrid(IGridView gridView);
  void setToolbox(IToolboxView toolbox);
}
