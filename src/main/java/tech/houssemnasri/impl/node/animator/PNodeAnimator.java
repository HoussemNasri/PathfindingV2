package tech.houssemnasri.impl.node.animator;

import java.util.Objects;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import tech.houssemnasri.api.node.INodeView;
import tech.houssemnasri.impl.animation.AnimationSuite;
import tech.houssemnasri.impl.node.PNode;
import tech.houssemnasri.impl.node.PNodeView;
import tech.houssemnasri.impl.node.Position;
import tech.houssemnasri.impl.node.painter.BaseNodePainter;

public class PNodeAnimator extends BaseNodeAnimator {
  private INodeView overlayNode;
  private BaseNodePainter toAnimatePainter;

  public PNodeAnimator(INodeView targetNode, AnimationSuite animationSuite) {
    super(targetNode, animationSuite);
  }

  private void setToAnimatePainter(BaseNodePainter toAnimatePainter) {
    this.toAnimatePainter = toAnimatePainter;
  }

  public BaseNodePainter getToAnimatePainter() {
    return toAnimatePainter;
  }

  private void setOverlayNode(INodeView overlayNode) {
    this.overlayNode = overlayNode;
  }

  public INodeView getOverlayNode() {
    return overlayNode;
  }

  private void initOverlay() {
    PNode fakeNode = new PNode(Position.ERROR, getToType());
    INodeView fakeView = new PNodeView(fakeNode, false);
    setToAnimatePainter(fakeView);
    StackPane targetRoot = getTargetNode().getRoot();
    targetRoot.getChildren().add(fakeView.getRoot());
    Rectangle clip = new Rectangle(targetRoot.getPrefWidth(), targetRoot.getPrefHeight());
    targetRoot.setClip(isClippingEnabled() ? clip : null);

    setOverlayNode(fakeView);
  }

  private void setToAnimatePainter(INodeView fakeView) {
    setToAnimatePainter(getTargetNode().getPainter().create(fakeView));
    fakeView.setPainter(getToAnimatePainter());
  }

  @Override
  public void animate() {
    if (Objects.nonNull(getAnimation()) && getAnimation().isPlaying()) {
      finishAnimationImmediately();
    }
    initOverlay();
    initAnimationFor(getOverlayNode().getRoot());
    getAnimation().play();
    getAnimation().setOnFinished(event -> onAnimationFinished());
  }

  private void finishAnimationImmediately() {
    getAnimation().stop();
    getTargetNode().getRoot().getChildren().remove(getOverlayNode().getRoot());
    getTargetNode().getPainter().paint(overlayNode.getNodeModel().getType());
  }

  @Override
  public void onAnimationFinished() {
    getTargetNode().getRoot().getChildren().remove(getOverlayNode().getRoot());
    // If by the time the animation finished, the node type changes then we don't paint the
    // node.
    if (getToType() == getTargetNode().getNodeModel().getType()) {
      getTargetNode().getPainter().paint(getToType());
    }
  }
}
