package tech.houssemnasri.api.grid;

import javafx.scene.layout.Region;

// Fun Fact: The view in MVP should only have views, the state needs to be managed by the Presenter.
public interface IGridView {
    /** Render the current state of the grid model on the screen. */
    void refresh();

    /** Returns the JavaFx root node. */
    Region getRoot();

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

    /** Enforcing the MVP architecture pattern by having a reference to the view presenter. */
    void setPresenter(IGridPresenter presenter);
}
