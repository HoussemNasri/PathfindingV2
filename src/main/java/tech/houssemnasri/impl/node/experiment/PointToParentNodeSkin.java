package tech.houssemnasri.impl.node.experiment;

/** This is skin will have an arrow that will point toward this node's parent. */
public class PointToParentNodeSkin extends BaseNodeSkin {
  protected PointToParentNodeSkin(NodeView control) {
    super(control);
  }

  @Override
  public BaseNodeSkin newInstance(NodeView control) {
    return new PointToParentNodeSkin(control);
  }
}
