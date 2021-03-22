package tech.houssemnasri.impl.animation;

import javafx.util.Duration;

import animatefx.animation.BounceIn;
import animatefx.animation.RotateIn;
import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;

public class AnimationSuite {
  // Basic Node Animations
  private AnimationFXProxy onEnterBasic;
  private AnimationFXProxy onExitBasic;

  // Wall Node Animations
  private AnimationFXProxy onEnterWall;
  private AnimationFXProxy onExitWall;

  // Source Node Animations
  private AnimationFXProxy onEnterSource;
  private AnimationFXProxy onExitSource;

  // Destination Node Animations
  private AnimationFXProxy onEnterDestination;
  private AnimationFXProxy onExitDestination;

  // Open Node Animations
  private AnimationFXProxy onEnterOpen;
  private AnimationFXProxy onExitOpen;

  // Closed Node Animations
  private AnimationFXProxy onEnterClosed;
  private AnimationFXProxy onExitClosed;

  // Path Node Animations
  private AnimationFXProxy onEnterPath;
  private AnimationFXProxy onExitPath;

  private AnimationSuite() {
    onEnterBasic = new AnimationFXProxy(new ZoomIn());
    onEnterBasic.setSpeed(2.1);
    onExitBasic = new AnimationFXProxy(new ZoomOut());

    onEnterWall = new AnimationFXProxy(new ZoomIn());
    onEnterWall.setSpeed(2.1);
    onExitWall = new AnimationFXProxy(new ZoomOut());

    onEnterSource = new AnimationFXProxy(new ZoomIn());
    onEnterSource.setJumpTo(Duration.millis(200));
    onExitSource = new AnimationFXProxy(new ZoomOut());

    onEnterDestination = new AnimationFXProxy(new ZoomIn());
    onEnterDestination.setJumpTo(Duration.millis(200));
    onExitDestination = new AnimationFXProxy(new ZoomOut());

    onEnterOpen = new AnimationFXProxy(new RotateIn());
    onExitOpen = new AnimationFXProxy(new ZoomOut());

    onEnterClosed = new AnimationFXProxy(new RotateIn());
    onExitClosed = new AnimationFXProxy(new ZoomOut());

    onEnterPath = new AnimationFXProxy(new BounceIn());
    onExitPath = new AnimationFXProxy(new ZoomOut());
  }

  public AnimationFXProxy getOnEnterBasic() {
    return onEnterBasic;
  }

  public AnimationFXProxy getOnExitBasic() {
    return onExitBasic;
  }

  public AnimationFXProxy getOnEnterWall() {
    return onEnterWall;
  }

  public AnimationFXProxy getOnExitWall() {
    return onExitWall;
  }

  public AnimationFXProxy getOnEnterSource() {
    return onEnterSource;
  }

  public AnimationFXProxy getOnExitSource() {
    return onExitSource;
  }

  public AnimationFXProxy getOnEnterDestination() {
    return onEnterDestination;
  }

  public AnimationFXProxy getOnExitDestination() {
    return onExitDestination;
  }

  public AnimationFXProxy getOnEnterOpen() {
    return onEnterOpen;
  }

  public AnimationFXProxy getOnExitOpen() {
    return onExitOpen;
  }

  public AnimationFXProxy getOnEnterClosed() {
    return onEnterClosed;
  }

  public AnimationFXProxy getOnExitClosed() {
    return onExitClosed;
  }

  public AnimationFXProxy getOnEnterPath() {
    return onEnterPath;
  }

  public AnimationFXProxy getOnExitPath() {
    return onExitPath;
  }

  public static class Builder {
    AnimationSuite build = new AnimationSuite();

    public Builder setOnEnterWall(AnimationFXProxy animation) {
      build.onEnterWall = animation;
      return this;
    }

    public AnimationSuite build() {
      return this.build;
    }
  }
}
