package tech.houssemnasri.impl.node.experiment;

/** This skin will display cost information on the node. */
public class WithCostNodeSkin extends BaseNodeSkin {
  protected WithCostNodeSkin(NodeView control) {
    super(control);
  }

  @Override
  public BaseNodeSkin newInstance(NodeView control) {
    return new WithCostNodeSkin(control);
  }
}
