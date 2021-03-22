package tech.houssemnasri.api.grid;

import javafx.scene.layout.Region;

import tech.houssemnasri.api.mvp.PresentableView;

// Fun Fact: The view in MVP should only have views, the state needs to be managed by the Presenter.
public interface IGridView extends PresentableView<IGridPresenter> {

  /** Refresh the root node and returns it. */
  default Region getUpdatedRoot() {
    refresh();
    return getRoot();
  }

  /**
   * Showing cost info on the views when using an algorithm that uses costs.
   *
   * @param show If true cost information will be visible.
   */
  void setShowCostInfo(boolean show);
}
