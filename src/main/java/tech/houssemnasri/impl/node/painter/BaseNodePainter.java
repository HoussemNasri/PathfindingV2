package tech.houssemnasri.impl.node.painter;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.node.INodeView;
import tech.houssemnasri.api.theme.ITheme;
import tech.houssemnasri.impl.node.PNodeView;

/**
 * This class {@code BaseNodePainter} is responsible for painting {@code INodeView} according to
 * it's type and the current theme.
 */
public abstract class BaseNodePainter {
  private final ObjectProperty<ITheme> themeProperty = new SimpleObjectProperty<>();
  private INodeView nodeView;

  public BaseNodePainter(INodeView nodeView, ITheme theme) {
    setNodeView(nodeView);
    switchTheme(theme);
  }

  private void setNodeView(INodeView nodeView) {
    this.nodeView = nodeView;
  }

  public abstract void paint(INode.Type nodeType);

  public void switchTheme(ITheme newTheme) {
    themeProperty.set(newTheme);
  }

  public ITheme getTheme() {
    return themeProperty.get();
  }

  public ObjectProperty<ITheme> themeProperty() {
    return themeProperty;
  }

  public INodeView getNodeView() {
    return nodeView;
  }

  public abstract BaseNodePainter create(INodeView nodeView);
}
