package tech.houssemnasri.impl.animation;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

import animatefx.animation.AnimationFX;

/**
 * This proxy class is used to interact with the AnimateFX library safely, in the original {@code
 * AnimationFx} you can't {@code setSpeed()} or {@code setDelay()} before initializing the node to
 * animate, doing that will cause a {@link NullPointerException}. I couldn't inherit {@link
 * AnimationFX} because there are some package-private abstract methods.
 */
public class AnimationFXProxy {
    private double speed = 1.0f;
    private Duration delay = Duration.ZERO;
    private Duration jumpToTime = Duration.ZERO;

    private final AnimationFX animationFX;

    public AnimationFXProxy(AnimationFX animationFX) {
        this.animationFX = animationFX;
    }

    public void setSpeed(double value) {
        if (animationFX.getTimeline() != null) {
            animationFX.setSpeed(value);
        }
        this.speed = value;
    }

    public double getSpeed() {
        if (getTimeline() != null) {
            return getTimeline().getRate();
        }
        return speed;
    }

    public Duration getDelay() {
        if (getTimeline() != null) {
            return getTimeline().getDelay();
        }
        return delay;
    }

    public void setDelay(Duration delay) {
        if (animationFX.getTimeline() != null) {
            animationFX.setDelay(delay);
        }
        this.delay = delay;
    }

    public void setTimeline(Timeline timeline) {
        animationFX.setTimeline(timeline);
        animationFX.setSpeed(speed);
        animationFX.setDelay(delay);
    }

    public final void setOnFinished(EventHandler<ActionEvent> value) {
        if (animationFX.getTimeline() != null) {
            animationFX.getTimeline().setOnFinished(value);
        }
    }

    public void setCycleCount(int value) {
        this.animationFX.setCycleCount(value);
    }

    public void setNode(Node node) {
        animationFX.setNode(node);
        setSpeed(speed);
        setDelay(delay);
        setJumpTo(jumpToTime);
    }

    public Timeline getTimeline() {
        return animationFX.getTimeline();
    }

    public boolean isResetOnFinished() {
        return animationFX.isResetOnFinished();
    }

    public Node getNode() {
        return animationFX.getNode();
    }

    /** Function to reset the node or not when the animation is finished */
    public void setResetOnFinished(boolean reset) {
        animationFX.setResetOnFinished(reset);
    }

    /** Play the animation */
    public void play() {
        animationFX.play();
    }

    public void setJumpTo(Duration time) {
        if (getTimeline() != null) {
            getTimeline().jumpTo(time);
        } else {
            jumpToTime = time;
        }
    }

    /** Stop the animation */
    public void stop() {
        animationFX.stop();
    }
}
