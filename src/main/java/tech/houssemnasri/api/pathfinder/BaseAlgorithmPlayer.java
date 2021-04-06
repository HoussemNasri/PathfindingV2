package tech.houssemnasri.api.pathfinder;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;

/**
 * This class {@code BaseAlgorithmPlayer} is responsible for managing the player state and perform
 * the player actions like (pause, play, forward, back, reset).
 */
public abstract class BaseAlgorithmPlayer extends AnimationTimer {
  private BaseAlgorithm thisAlgorithm;
  protected Status playerStatus = Status.IDLE;
  private final List<OnPlayerFinishedListener> finishedListeners = new ArrayList<>();

  public BaseAlgorithmPlayer(BaseAlgorithm thisAlgorithm) {
    this.thisAlgorithm = thisAlgorithm;
  }

  public abstract void forward();

  public abstract void back();

  public void play() {
    super.start();
    playerStatus = Status.RUNNING;
  }

  public void pause() {
    super.stop();
    this.playerStatus = Status.PAUSED;
  }

  public void reset() {
    super.stop();
    this.playerStatus = Status.IDLE;
  }

  public void setAlgorithm(BaseAlgorithm algorithm) {
    thisAlgorithm = algorithm;
  }

  public BaseAlgorithm getAlgorithm() {
    return thisAlgorithm;
  }

  public Status getStatus() {
    return playerStatus;
  }

  public void registerOnFinishedListener(OnPlayerFinishedListener onFinishedListener) {
    finishedListeners.add(onFinishedListener);
  }

  public void unregisterOnFinishedListener(OnPlayerFinishedListener onFinishedListener) {
    finishedListeners.remove(onFinishedListener);
  }


  protected void notifyAllOnFinishedListeners() {
    finishedListeners.forEach(OnPlayerFinishedListener::onFinished);
  }

  public enum Status {
    IDLE,
    RUNNING,
    PAUSED
  }
}
