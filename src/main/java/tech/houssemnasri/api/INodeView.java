package tech.houssemnasri.api;

import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.StackPane;

/**
 * The {@code INodeView} is a self contained view we use to display our model {@code INode}, it
 * listens for changes on the model and update itself accordingly.
 */
public interface INodeView {
    /**
     * Showing cost info on the view.
     *
     * @param show If true cost information will be visible.
     */
    void showCostInfo(boolean show);

    INode getNodeModel();

    void setTheme(ITheme newTheme);

    ObjectProperty<ITheme> themeProperty();
}
