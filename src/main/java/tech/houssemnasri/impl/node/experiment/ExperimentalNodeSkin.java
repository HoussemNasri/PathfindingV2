package tech.houssemnasri.impl.node.experiment;

public class ExperimentalNodeSkin extends BaseExperimentalNodeSkin {

  protected ExperimentalNodeSkin(ExprNodeView control) {
    super(control);
  }

  @Override
  public ExprNodeView getControlCopy() {
    ExprNodeView copy = new ExprNodeView(getSkinnable());
    copy.setSkin(new ExperimentalNodeSkin(copy));

    return copy;
  }
}
