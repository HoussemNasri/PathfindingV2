package tech.houssemnasri.api.pathfinder;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

import tech.houssemnasri.math.Clamp;

/**
 * This class {@code Visualizer} is responsible for managing the player state and perform the player
 * actions like (pause, play, forward, back, reset).
 */
public abstract class Visualizer extends AnimationTimer {
  private static final Long MIN_DELAY = 65000L;
  private static final Long MAX_DELAY = 65000000L;
  private static final Integer MIN_SPEED = 1;
  private static final Integer MAX_SPEED = 10;

  private BaseAlgorithm currentAlgorithm;
  protected Status playerStatus = Status.IDLE;
  private final List<OnFinishListener> finishListeners = new ArrayList<>();
  private final ReadOnlyIntegerWrapper speedProperty = new ReadOnlyIntegerWrapper(10);

  public Visualizer(BaseAlgorithm currentAlgorithm) {
    setAlgorithm(currentAlgorithm);
  }

  public void visualize() {
    if (currentAlgorithm.isPathFound()) {
      currentAlgorithm.reset();
    }
    startPlayer();
  }

  public void pause() {
    pausePlayer();
  }

  public void reset() {
    stopPlayer();
    currentAlgorithm.reset();
  }

  public abstract void forward();

  public abstract void back();

  public void setAlgorithm(BaseAlgorithm algorithm) {
    currentAlgorithm = algorithm;
  }

  public BaseAlgorithm getAlgorithm() {
    return currentAlgorithm;
  }

  public Status getStatus() {
    return playerStatus;
  }

  /**
   * Speed has to be a value between {@code 1} and {@code 10}, one being the slowest and ten being
   * the fastest.
   */
  public void setSpeed(int speed) {
    speedProperty.set(
        new Clamp(speed).apply(MIN_SPEED.doubleValue(), MAX_SPEED.doubleValue()).intValue());
  }

  public int getSpeed() {
    return speedProperty.get();
  }

  public ReadOnlyIntegerProperty speedProperty() {
    return speedProperty.getReadOnlyProperty();
  }

  protected long getDelay() {
    long delayDiff = MAX_DELAY - MIN_DELAY;
    long chunkSize = delayDiff / (MAX_SPEED - MIN_SPEED);
    return MAX_DELAY - chunkSize * (getSpeed() - 1);
  }

  public void registerFinishListener(OnFinishListener listener) {
    finishListeners.add(listener);
  }

  public void unregisterFinishListener(OnFinishListener listener) {
    finishListeners.remove(listener);
  }

  protected void notifyFinishListener() {
    finishListeners.forEach(OnFinishListener::onFinish);
  }

  protected void finishVisualization() {
    stopPlayer();
    notifyFinishListener();
    getAlgorithm().tracePath();
  }

  private void startPlayer() {
    super.start();
    playerStatus = Status.RUNNING;
  }

  private void pausePlayer() {
    super.stop();
    this.playerStatus = Status.PAUSED;
  }

  private void stopPlayer() {
    super.stop();
    this.playerStatus = Status.IDLE;
  }

  public enum Status {
    IDLE,
    RUNNING,
    PAUSED
  }

  public interface OnFinishListener {
    void onFinish();
  }
}
