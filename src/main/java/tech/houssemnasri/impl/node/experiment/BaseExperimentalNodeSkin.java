package tech.houssemnasri.impl.node.experiment;

import javafx.scene.control.SkinBase;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import tech.houssemnasri.Settings;
import tech.houssemnasri.api.node.experiment.IExprNodeView;
import tech.houssemnasri.impl.animation.AnimationFXProxy;

public abstract class BaseExperimentalNodeSkin extends SkinBase<ExprNodeView>{
    private static final double MINIMUM_HEIGHT = 25d;
    private static final double MINIMUM_WIDTH = 25d;
    private IExprNodeView cachedOverlay;
    private AnimationFXProxy animation;
    private final StackPane container;

    protected BaseExperimentalNodeSkin(ExprNodeView control) {
        super(control);
        control.setPrefSize(80, 80);
        container =
                new StackPane() {
                    {
                        getStyleClass().setAll("container");
                    }
                };
        control.typeProperty().addListener((a, b, c)->{
            if(control.isAnimated()){
                animate();
            }else {
                control.applyStyle();
            }
        });
    }

    protected final void animate() {
        if(animation != null && animation.isPlaying()){
            //Interrupt
            onAnimationInterrupted();
        }
        IExprNodeView overlay = newOverlay();
        cacheOverlay(overlay);
        prepareAnimation(overlay);
        startAnimation();
        animation.setOnFinished(e-> onAnimationFinished());
    }

    private void startAnimation() {
        animation.play();
        onAnimationStarted();
    }

    protected final StackPane getContainer() {
        return container;
    }

    private void prepareAnimation(IExprNodeView overlay) {
        animation = switch(overlay.getType()){
            case BASIC -> Settings.getAnimationSuite().getOnEnterBasic();
            case WALL -> Settings.getAnimationSuite().getOnEnterWall();
            case OPEN -> Settings.getAnimationSuite().getOnEnterOpen();
            case CLOSED -> Settings.getAnimationSuite().getOnEnterClosed();
            case PATH -> Settings.getAnimationSuite().getOnEnterPath();
            case SOURCE ->Settings.getAnimationSuite().getOnEnterSource();
            case DESTINATION -> Settings.getAnimationSuite().getOnEnterDestination();
        };
        animation.setNode(overlay.getRoot());
    }

    public abstract ExprNodeView getControlCopy();
    public IExprNodeView newOverlay() {
        IExprNodeView overlayView = getControlCopy();
        overlayView.setAnimated(false);
        Rectangle clipRect = new Rectangle();
        clipRect.setWidth(overlayView.getRoot().getPrefWidth());
        clipRect.setHeight(overlayView.getRoot().getPrefHeight());
        getSkinnable().setClip(clipRect);
        getChildren().add(overlayView.getRoot());

        return overlayView;
    }

    private void cacheOverlay(IExprNodeView value){
        this.cachedOverlay = value;
    }


    public void onAnimationStarted() {
        System.out.println("onAnimationStarted()");
    }

    public void onAnimationFinished() {
        System.out.println("onAnimationFinished()");
        if(cachedOverlay == null){
            return;
        }
        getChildren().remove(cachedOverlay.getRoot());
        getSkinnable().applyStyle();
        System.out.println(getChildren().size());
    }


    public void onAnimationInterrupted() {
        System.out.println("onAnimationInterrupted()");
        if(cachedOverlay == null){
            return;
        }
        animation.stop();
        getChildren().remove(cachedOverlay.getRoot());
        getSkinnable().applyStyle(cachedOverlay.getType());
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

    /** {@inheritDoc} */
    @Override
    protected double computeMaxWidth(
            double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return getSkinnable().prefWidth(height);
    }

    /** {@inheritDoc} */
    @Override
    protected double computeMaxHeight(
            double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return getSkinnable().prefHeight(width);
    }

}
