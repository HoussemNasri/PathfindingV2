package tech.houssemnasri.impl.node.painter;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.theme.ITheme;
import tech.houssemnasri.impl.animation.AnimationSuite;
import tech.houssemnasri.impl.node.PNode;
import tech.houssemnasri.impl.node.PNodeView;
import tech.houssemnasri.impl.node.PPosition;
import tech.houssemnasri.impl.node.proxy.AnimationFXProxy;

public class AnimatedNodePainter extends BaseNodePainter {
    private PNodeView nodeOverlay;
    private boolean isClippingEnabled = false;

    private AnimationSuite animationSuite;
    private AnimationFXProxy animation;

    public AnimatedNodePainter(ITheme theme, PNodeView nodeView, AnimationSuite animationSuite) {
        super(theme, nodeView);
        setAnimationSuite(animationSuite);
    }

    private void setAnimationSuite(AnimationSuite animationSuite) {
        this.animationSuite = animationSuite;
    }

    /**
     * Perform som cleanup after the transition finishes and repaint the view with the new paint if
     * applicable.
     */
    private void doFinishTransition(INode.Type newState) {
        getNodeView().getChildren().remove(getOverlay());
        // If by the time the animation finished, the node type changes then we don't paint the
        // node.
        if (newState == getNodeView().getNodeModel().getType()) {
            new SimpleNodePainter(getTheme(), getNodeView()).paint(newState);
        }
    }

    protected Animation createAnimation(){
        if(getOverlay() == null) return null;
        setClippingEnabled(true);
        return new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(getOverlay().scaleXProperty(), 0.2),
                        new KeyValue(getOverlay().scaleYProperty(), 0.2)),
                new KeyFrame(
                        Duration.millis(400),
                        new KeyValue(getOverlay().scaleXProperty(), 1),
                        new KeyValue(getOverlay().scaleYProperty(), 1)));
    }

    private PNodeView createTransitionOverlay(INode.Type newState) {
        PNodeView transitionOverlay = new PNodeView(new PNode(PPosition.ERROR, newState));
        transitionOverlay.setPainter(new SimpleNodePainter(getTheme(), transitionOverlay));
        getNodeView().getChildren().add(transitionOverlay);
        getNodeView()
                .setClip(
                        isClippingEnabled
                                ? new Rectangle(
                                        getNodeView().getPrefWidth(), getNodeView().getPrefHeight())
                                : null);
        return transitionOverlay;
    }

    protected Pane getOverlay() {
        return nodeOverlay;
    }

    public void setClippingEnabled(boolean clippingEnabled) {
        isClippingEnabled = clippingEnabled;
    }

    public boolean isClippingEnabled() {
        return isClippingEnabled;
    }

    private AnimationFXProxy getAnimation(INode.Type nodeType){
        return switch(nodeType){
            case BASIC ->animationSuite.getOnEnterBasic();
            case WALL -> animationSuite.getOnEnterWall();
            case OPEN -> animationSuite.getOnEnterOpen();
            case CLOSED -> animationSuite.getOnEnterClosed();
            case PATH -> animationSuite.getOnEnterPath();
            case SOURCE ->animationSuite.getOnEnterSource();
            case DESTINATION -> animationSuite.getOnEnterDestination();
        };
    }

    @Override
    public void paint(INode.Type nodeType) {
        if (animation != null) {
            if (animation.getTimeline().getStatus() == Animation.Status.RUNNING) {
                doFinishTransition(nodeType);
                animation.stop();
            }
        }
        this.nodeOverlay = createTransitionOverlay(nodeType);
        this.animation = getAnimation(nodeType);
        this.animation.setNode(getOverlay());
        if (animation != null) {
            animation.play();
            this.animation.setOnFinished(e -> doFinishTransition(nodeType));
        } else {
            new SimpleNodePainter(getTheme(), getNodeView()).paint(nodeType);
        }
    }
}
