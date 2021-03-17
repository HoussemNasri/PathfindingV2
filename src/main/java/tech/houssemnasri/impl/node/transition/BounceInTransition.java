package tech.houssemnasri.impl.node.transition;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.impl.node.NodePainter;

public class BounceInTransition extends BaseNodeTransition {

    public BounceInTransition(
            StackPane theView, INode theModel, INode.Type theNewState, NodePainter painter) {
        super(theView, theModel, theNewState, painter);
    }

    @Override
    protected Animation createAnimation() {
        Pane overlay = getOverlay();
        return new Timeline();
    }
}
