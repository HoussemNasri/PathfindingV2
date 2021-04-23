package tech.houssemnasri.node.skins;

import tech.houssemnasri.node.PNodeView;

/** This is skin will have an arrow that will point toward this node's parent. */
public class PointToParentNodeSkin extends BaseNodeSkin {
  protected PointToParentNodeSkin(PNodeView control) {
    super(control);
  }

  @Override
  public BaseNodeSkin newInstance(PNodeView control) {
    return new PointToParentNodeSkin(control);
  }
}
