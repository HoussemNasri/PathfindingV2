package tech.houssemnasri.impl.node.transition;

import javafx.animation.Animation;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.impl.node.NodePainter;
import tech.houssemnasri.impl.node.PNode;
import tech.houssemnasri.impl.node.PNodeView;
import tech.houssemnasri.impl.node.PPosition;

public abstract class BaseNodeTransition {
    protected Animation animation;

    private final StackPane theView;
    private final INode theModel;
    private final INode.Type theNewState;
    private final NodePainter painter;

    private final Pane transitionOverlay;

    public BaseNodeTransition(
            StackPane theView, INode theModel, INode.Type theNewState, NodePainter painter) {
        this.theView = theView;
        this.theModel = theModel;
        this.theNewState = theNewState;
        this.painter = painter;
        this.animation = createAnimation();
        this.transitionOverlay = createTransitionOverlay();
        this.animation.setOnFinished(this::doSwitchState);
    }

    private void doSwitchState(ActionEvent actionEvent) {
        theModel.setType(theNewState);
        theView.getChildren().remove(transitionOverlay);
    }

    public void play() {
        if (animation == null) {
            return;
        }
        animation.play();
    }

    public void pause() {
        if (animation == null) {
            return;
        }
        animation.pause();
    }

    public void stop() {
        if (animation == null) {
            return;
        }
        animation.stop();
    }

    protected void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public Animation getAnimation() {
        return animation;
    }

    protected abstract Animation createAnimation();

    private Pane createTransitionOverlay() {
        PNodeView transitionOverlay =
                new PNodeView(new PNode(PPosition.ERROR, theNewState), painter);
        theView.getChildren().add(transitionOverlay);
        theView.setClip(new Rectangle(theView.getPrefWidth(), theView.getPrefHeight()));
        return transitionOverlay;
    }

    protected Pane getOverlay() {
        return transitionOverlay;
    }

    public INode getNodeModel() {
        return theModel;
    }

    public StackPane getNodeView() {
        return theView;
    }

    public INode.Type getNewState() {
        return theNewState;
    }
}
