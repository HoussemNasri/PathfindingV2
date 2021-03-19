package tech.houssemnasri.impl.node.painter;

import javafx.animation.Animation;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.theme.ITheme;
import tech.houssemnasri.impl.animation.AnimationSuite;
import tech.houssemnasri.impl.node.PNode;
import tech.houssemnasri.impl.node.PNodeView;
import tech.houssemnasri.impl.node.PPosition;
import tech.houssemnasri.impl.animation.AnimationFXProxy;

public class AnimatedNodePainter extends BaseNodePainter {
    private boolean isClippingEnabled = true;
    private PNodeView transitionNode;
    private AnimationSuite animationSuite;
    private AnimationFXProxy animation;

    public AnimatedNodePainter(PNodeView nodeView, ITheme theme, AnimationSuite animationSuite) {
        super(nodeView, theme);
        setAnimationSuite(animationSuite);
    }

    private void setAnimationSuite(AnimationSuite animationSuite) {
        this.animationSuite = animationSuite;
    }

    /**
     * Perform som cleanup after the transition finishes and repaint the view with the new paint if
     * applicable.
     */
    private void onAnimationFinished(INode.Type newState) {
        getNodeView().getChildren().remove(getTransitionNode());
        // If by the time the animation finished, the node type changes then we don't paint the
        // node.
        if (newState == getNodeView().getNodeModel().getType()) {
            new SimpleNodePainter(getTheme(), getNodeView()).paint(newState);
        }
    }

    private PNodeView createTransitionNode(INode.Type newState) {
        PNodeView view = new PNodeView(new PNode(PPosition.ERROR, newState));
        view.setPainter(new SimpleNodePainter(getTheme(), view));
        getNodeView().getChildren().add(view);
        Rectangle clip = new Rectangle(getNodeView().getPrefWidth(), getNodeView().getPrefHeight());
        getNodeView().setClip(isClippingEnabled ? clip : null);
        return view;
    }

    protected Pane getTransitionNode() {
        return transitionNode;
    }

    public void setClippingEnabled(boolean clippingEnabled) {
        isClippingEnabled = clippingEnabled;
    }

    public boolean isClippingEnabled() {
        return isClippingEnabled;
    }

    private AnimationFXProxy createAnimation(INode.Type nodeType){
        AnimationFXProxy animation = switch(nodeType){
            case BASIC ->animationSuite.getOnEnterBasic();
            case WALL -> animationSuite.getOnEnterWall();
            case OPEN -> animationSuite.getOnEnterOpen();
            case CLOSED -> animationSuite.getOnEnterClosed();
            case PATH -> animationSuite.getOnEnterPath();
            case SOURCE ->animationSuite.getOnEnterSource();
            case DESTINATION -> animationSuite.getOnEnterDestination();
        };
        animation.setNode(getTransitionNode());
        return animation;
    }

    @Override
    public void paint(INode.Type nodeType) {
        if (animation != null) {
            if (animation.getTimeline().getStatus() == Animation.Status.RUNNING) {
                onAnimationFinished(nodeType);
                animation.stop();
            }
        }
        this.transitionNode = createTransitionNode(nodeType);
        this.animation = createAnimation(nodeType);
        animation.play();
        this.animation.setOnFinished(e -> onAnimationFinished(nodeType));
    }
}
