package tech.houssemnasri.api.pathfinder;

import javafx.animation.AnimationTimer;

/**
 * This class {@code BaseAlgorithmPlayer} is responsible for managing the player state and perform
 * the player actions like (pause, play, forward, back, reset).
 */
public abstract class BaseAlgorithmPlayer extends AnimationTimer {
  private BaseAlgorithm thisAlgorithm;

  public BaseAlgorithmPlayer(BaseAlgorithm thisAlgorithm) {
    this.thisAlgorithm = thisAlgorithm;
  }

  public abstract void forward();

  public abstract void back();

  public void play() {
    super.start();
  }

  public abstract void pause();

  public abstract void reset();

  public void setAlgorithm(BaseAlgorithm algorithm) {
    thisAlgorithm = algorithm;
  }

  public BaseAlgorithm getAlgorithm() {
    return thisAlgorithm;
  }

  enum Status {
    RUNNING,
    PAUSED,
    STOPPED
  }
}
