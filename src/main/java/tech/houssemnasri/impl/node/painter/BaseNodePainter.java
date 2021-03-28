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
  private INodeView nodeView;

  public BaseNodePainter(INodeView nodeView) {
    setNodeView(nodeView);
    switchTheme();
  }

  private void setNodeView(INodeView nodeView) {
    this.nodeView = nodeView;
  }

  public abstract void paint(INode.Type nodeType);

  public void switchTheme() {

  }

  public INodeView getNodeView() {
    return nodeView;
  }

  public abstract BaseNodePainter create(INodeView nodeView);
}
