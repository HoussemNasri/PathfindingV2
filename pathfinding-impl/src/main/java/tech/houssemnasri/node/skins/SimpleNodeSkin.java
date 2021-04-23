package tech.houssemnasri.node.skins;

import tech.houssemnasri.node.PNodeView;

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
