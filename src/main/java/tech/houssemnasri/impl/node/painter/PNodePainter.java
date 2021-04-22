package tech.houssemnasri.impl.node.painter;

import java.util.List;
import tech.houssemnasri.api.node.INodeView;

import static tech.houssemnasri.api.node.INode.*;

@Deprecated
public class PNodePainter extends BaseNodePainter {
  private final List<Type> showCostTypes = List.of(Type.OPEN, Type.CLOSED, Type.PATH);

  public PNodePainter(INodeView nodeView) {
    super(nodeView);
    nodeView
        .showCostProperty()
        .addListener((observable, oldValue, newValue) -> setShowCost(newValue));
  }

  @Override
  public void paint(Type nodeType) {
    String typeName = nodeType.name().toLowerCase();
    getNodeView().getRoot().getStyleClass().setAll(String.format("%s-node", typeName));
    setShowCost(showCostTypes.contains(nodeType));
  }

  private void setShowCost(boolean show) {
    if (!getNodeView().isShowCostEnabled() && show) {
      return;
    }
    getNodeView().getCenterText().setVisible(show);
    getNodeView().getTopLeftCornerText().setVisible(show);
    getNodeView().getTopRightCornerText().setVisible(show);
  }

  @Override
  public BaseNodePainter create(INodeView nodeView) {
    return new PNodePainter(nodeView);
  }
}
