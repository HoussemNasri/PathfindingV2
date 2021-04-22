package tech.houssemnasri.impl.node.animator;

import java.util.Objects;

import javafx.scene.Node;

import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.node.INodeView;
import tech.houssemnasri.impl.animation.AnimationFXProxy;
import tech.houssemnasri.impl.animation.AnimationSuite;
@Deprecated
public abstract class BaseNodeAnimator {
  private final INodeView targetNode;
  private final AnimationSuite animationSuite;
  private INode.Type toType;
  private boolean isClippingEnabled;
  private AnimationFXProxy currentAnimation = null;

  public BaseNodeAnimator(INodeView targetNode, AnimationSuite animationSuite) {
    this.targetNode = targetNode;
    this.animationSuite = animationSuite;
  }

  public INodeView getTargetNode() {
    return targetNode;
  }

  public AnimationSuite getAnimationSuite() {
    return animationSuite;
  }

  public INode.Type getToType() {
    return toType;
  }

  public void setToType(INode.Type toType) {
    this.toType = toType;
  }

  public boolean isClippingEnabled() {
    return isClippingEnabled;
  }

  public void setClippingEnabled(boolean clippingEnabled) {
    isClippingEnabled = clippingEnabled;
  }

  public void initAnimationFor(Node nodeToAnimate){
    this.currentAnimation = switch(getToType()){
      case BASIC ->animationSuite.getOnEnterBasic();
      case WALL -> animationSuite.getOnEnterWall();
      case OPEN -> animationSuite.getOnEnterOpen();
      case CLOSED -> animationSuite.getOnEnterClosed();
      case PATH -> animationSuite.getOnEnterPath();
      case SOURCE ->animationSuite.getOnEnterSource();
      case DESTINATION -> animationSuite.getOnEnterDestination();
    };
    this.currentAnimation.setNode(nodeToAnimate);
  }
  public AnimationFXProxy getAnimation(){
    return this.currentAnimation;
  }

  public void stop(){
    Objects.requireNonNull(getAnimation()).stop();
    onAnimationFinished();
  }
  public abstract void animate();
  public abstract void onAnimationFinished();
}
