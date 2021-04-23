package tech.houssemnasri.node;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.Control;

import tech.houssemnasri.mvp.View;

public interface INodeView extends View<Control> {
  INode getNodeModel();

  INode.Type getType();

  ReadOnlyObjectProperty<INode.Type> typeProperty();

  void setAnimated(boolean value);

  boolean isAnimated();

  BooleanProperty animatedProperty();
}
