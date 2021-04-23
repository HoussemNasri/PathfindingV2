package tech.houssemnasri.mainview;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import tech.houssemnasri.grid.IGridView;
import tech.houssemnasri.mvp.PresentableView;
import tech.houssemnasri.toolbox.IToolboxView;

public interface IMainView extends PresentableView<IMainViewPresenter, Pane>, Initializable {
  void setGrid(IGridView gridView);
  void setToolbox(IToolboxView toolbox);
}
