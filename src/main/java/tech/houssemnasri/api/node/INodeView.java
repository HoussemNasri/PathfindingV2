package tech.houssemnasri.api.node;

import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import tech.houssemnasri.api.mvp.View;
import tech.houssemnasri.impl.node.animator.BaseNodeAnimator;
import tech.houssemnasri.impl.node.painter.BaseNodePainter;

/**
 * The {@code INodeView} is a self contained view we use to display our model {@code INode}, it
 * listens for changes on the model and update itself accordingly.
 */
public interface INodeView extends View<StackPane> {
  /**
   * Showing cost info on the view.
   *
   * @param show If true cost information will be visible.
   */
  void setShowCostInfo(boolean show);

  boolean isShowCostEnabled();

  BooleanProperty showCostProperty();

  INode getNodeModel();

  void setPainter(BaseNodePainter painter);

  BaseNodePainter getPainter();

  Text getCenterText();

  Text getTopLeftCornerText();

  Text getTopRightCornerText();

  void setAnimator(BaseNodeAnimator animator);

  void setIsAnimate(boolean isAnimate);

  boolean isAnimate();
}
