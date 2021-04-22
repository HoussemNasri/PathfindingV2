package tech.houssemnasri.api.grid;

import javafx.scene.layout.Region;

import tech.houssemnasri.api.mvp.PresentableView;

// Fun Fact: The view in MVP should only have views, the state needs to be managed by the Presenter.
public interface IGridView extends PresentableView<IGridPresenter, Region> {
}
