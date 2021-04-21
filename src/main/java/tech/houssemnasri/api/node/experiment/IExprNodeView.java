package tech.houssemnasri.api.node.experiment;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.Control;

import tech.houssemnasri.api.mvp.View;
import tech.houssemnasri.api.node.INode;

public interface IExprNodeView extends View<Control> {
  INode getNodeModel();

  INode.Type getType();

  ReadOnlyObjectProperty<INode.Type> typeProperty();

  void setAnimated(boolean value);

  boolean isAnimated();

  BooleanProperty animatedProperty();
}
