package tech.houssemnasri.impl.node.experiment;

/** This is the simplest implementation of {@code BaseNodeSkin} */
public class SimpleNodeSkin extends BaseNodeSkin {

  public SimpleNodeSkin(NodeView control) {
    super(control);
  }

  @Override
  public BaseNodeSkin newInstance(NodeView control) {
    return new SimpleNodeSkin(control);
  }
}
