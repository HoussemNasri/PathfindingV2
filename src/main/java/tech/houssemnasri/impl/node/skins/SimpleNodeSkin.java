package tech.houssemnasri.impl.node.skins;

import tech.houssemnasri.impl.node.PNodeView;

/** This is the simplest implementation of {@code BaseNodeSkin} */
public class SimpleNodeSkin extends BaseNodeSkin {

  public SimpleNodeSkin(PNodeView control) {
    super(control);
  }

  @Override
  public BaseNodeSkin newInstance(PNodeView control) {
    return new SimpleNodeSkin(control);
  }
}
