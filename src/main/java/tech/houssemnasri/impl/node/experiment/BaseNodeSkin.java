package tech.houssemnasri.impl.node.experiment;

import javafx.beans.value.ObservableValue;
import javafx.scene.CacheHint;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import tech.houssemnasri.Settings;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.node.experiment.IExprNodeView;
import tech.houssemnasri.impl.animation.AnimationFXProxy;

public abstract class BaseNodeSkin extends SkinBase<NodeView> {
  private static final double MINIMUM_WIDTH = 25d;
  private static final double MINIMUM_HEIGHT = 25d;
  public static final double PREF_WIDTH = 25d;
  public static final double PREF_HEIGHT = 25d;
  private IExprNodeView cachedOverlay;
  private AnimationFXProxy animation;
  private StackPane container;

  protected BaseNodeSkin(NodeView control) {
    super(control);
    initGraphics();
    registerListeners();
    // Important!!! without this line we can't interact with the grid because all events would be
    // consumed by the node view.
    consumeMouseEvents(false);
  }

  private void initGraphics() {
    getSkinnable().setPrefSize(PREF_WIDTH, PREF_HEIGHT);
    getSkinnable().applyStyle();
    container = new StackPane();
    container.getStyleClass().setAll("container");
    getChildren().add(container);
  }

  private void registerListeners() {
    getSkinnable().typeProperty().addListener(this::handleTypeChanged);
    getContainer().prefWidthProperty().bind(getSkinnable().prefWidthProperty());
    getContainer().prefHeightProperty().bind(getSkinnable().prefHeightProperty());
  }

  private void handleTypeChanged(ObservableValue<?> obs, INode.Type old, INode.Type type) {
    if (getSkinnable().isAnimated()) {
      animate();
    } else {
      getSkinnable().applyStyle();
    }
  }

  protected final void animate() {
    if (animation != null && animation.isPlaying()) {
      interruptAnimation();
    }
    createAndCacheOverlay();
    prepareAnimation();
    startAnimation();
    animation.setOnFinished(e -> finishAnimation());
  }

  private void startAnimation() {
    System.out.println("Animation started.");
    animation.play();
  }

  public void interruptAnimation() {
    System.out.println("Animation interrupted.");
    if (cachedOverlay == null) {
      return;
    }
    animation.stop();
    getChildren().remove(cachedOverlay.getRoot());
    getSkinnable().applyStyle(cachedOverlay.getType());
  }

  public void finishAnimation() {
    System.out.println("Animation finished.");
    if (cachedOverlay == null) {
      return;
    }
    getChildren().remove(cachedOverlay.getRoot());
    getSkinnable().applyStyle();
  }

  private void prepareAnimation() {
    switch (cachedOverlay.getType()) {
      case BASIC:
        prepareHelper(Settings.getAnimationSuite().getOnEnterBasic(), cachedOverlay);
        break;
      case WALL:
        prepareHelper(Settings.getAnimationSuite().getOnEnterWall(), cachedOverlay);
        break;
      case OPEN:
        prepareHelper(Settings.getAnimationSuite().getOnEnterOpen(), cachedOverlay);
        break;
      case CLOSED:
        prepareHelper(Settings.getAnimationSuite().getOnEnterClosed(), cachedOverlay);
        break;
      case PATH:
        prepareHelper(Settings.getAnimationSuite().getOnEnterPath(), cachedOverlay);
        break;
      case SOURCE:
        prepareHelper(Settings.getAnimationSuite().getOnEnterSource(), cachedOverlay);
        break;
      case DESTINATION:
        prepareHelper(Settings.getAnimationSuite().getOnEnterDestination(), cachedOverlay);
        break;
    }
  }

  private void prepareHelper(AnimationFXProxy animation, IExprNodeView node) {
    this.animation = animation;
    this.animation.setNode(node.getRoot());
  }

  private void createAndCacheOverlay() {
    cacheOverlay(newOverlay());
  }

  private void cacheOverlay(IExprNodeView value) {
    this.cachedOverlay = value;
  }

  protected final StackPane getContainer() {
    return container;
  }

  public abstract BaseNodeSkin newInstance(NodeView control);

  private IExprNodeView newOverlay() {
    NodeView overlayView = new NodeView(getSkinnable());
    overlayView.setSkin(newInstance(overlayView));
    Rectangle clipRect = new Rectangle();
    clipRect.setWidth(overlayView.getPrefWidth());
    clipRect.setHeight(overlayView.getPrefHeight());
    getSkinnable().setClip(clipRect);
    getChildren().add(overlayView.getRoot());
    // Optimization for smooth animation.
    overlayView.setCache(true);
    overlayView.setCacheShape(true);
    overlayView.setCacheHint(CacheHint.SPEED);

    return overlayView;
  }

  @Override
  protected double computeMinWidth(
      final double height,
      final double top,
      final double right,
      final double bottom,
      final double left) {
    return MINIMUM_WIDTH;
  }

  @Override
  protected double computeMinHeight(
      final double width,
      final double top,
      final double right,
      final double bottom,
      final double left) {
    return MINIMUM_HEIGHT;
  }

  @Override
  protected double computeMaxWidth(
      double height, double topInset, double rightInset, double bottomInset, double leftInset) {
    return getSkinnable().prefWidth(height);
  }

  @Override
  protected double computeMaxHeight(
      double width, double topInset, double rightInset, double bottomInset, double leftInset) {
    return getSkinnable().prefHeight(width);
  }
}
