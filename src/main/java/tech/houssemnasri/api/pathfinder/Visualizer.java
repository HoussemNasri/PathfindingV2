package tech.houssemnasri.api.pathfinder;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
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
  private static final Double MIN_SPEED = 1d;
  private static final Double MAX_SPEED = 10d;

  private BaseAlgorithm currentAlgorithm;
  protected Status playerStatus = Status.IDLE;
  private final List<VisualizerListener> listeners = new ArrayList<>();
  private final ReadOnlyDoubleWrapper speedProperty = new ReadOnlyDoubleWrapper(10d);

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
    notifyResetListeners();
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
   * Speed has to be a value between {@code 1.0d} and {@code 10.0d}, one being the slowest and ten
   * being the fastest.
   */
  public void setSpeed(double speed) {
    speedProperty.set(new Clamp(speed).apply(MIN_SPEED, MAX_SPEED));
  }

  public double getSpeed() {
    return speedProperty.get();
  }

  public ReadOnlyDoubleProperty speedProperty() {
    return speedProperty.getReadOnlyProperty();
  }

  protected long getDelay() {
    long delayDiff = MAX_DELAY - MIN_DELAY;
    long chunkSize = (long) (delayDiff / (MAX_SPEED - MIN_SPEED));
    return (long) (MAX_DELAY - chunkSize * (getSpeed() - 1d));
  }

  public void registerFinishListener(VisualizerListener listener) {
    listeners.add(listener);
  }

  public void unregisterFinishListener(VisualizerListener listener) {
    listeners.remove(listener);
  }

  protected void notifyFinishListeners() {
    listeners.forEach(VisualizerListener::onFinish);
  }

  protected void notifyResetListeners() {
    listeners.forEach(VisualizerListener::onReset);
  }

  protected void finishVisualization() {
    stopPlayer();
    notifyFinishListeners();
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

  public interface VisualizerListener {
    void onFinish();

    void onReset();
  }
}
