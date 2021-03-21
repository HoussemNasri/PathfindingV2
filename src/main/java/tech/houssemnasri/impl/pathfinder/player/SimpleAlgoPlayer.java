package tech.houssemnasri.impl.pathfinder.player;

import javafx.animation.AnimationTimer;

import tech.houssemnasri.api.pathfinder.BaseAlgorithm;
import tech.houssemnasri.api.pathfinder.BaseAlgorithmPlayer;

public class SimpleAlgoPlayer extends BaseAlgorithmPlayer {
  private static final long SPEED_SLOW = 700000000;
  private static final long SPEED_MEDIUM = 70000000;
  private static final long SPEED_FAST = 9000000;

  public SimpleAlgoPlayer(BaseAlgorithm algorithm) {
    super(algorithm);
  }

  @Override
  public void forward() {
    getAlgorithm().forward();
  }

  @Override
  public void back() {
    getAlgorithm().back();
  }

  @Override
  public void play() {
    AnimationTimer timer =
        new AnimationTimer() {
          private long start = -1;
          @Override
          public void handle(long now) {
            if (start == -1 || now - start >= SPEED_FAST) {
              start = now;
              if (getAlgorithm().isPathFound()) {
                this.stop();
              } else {
                getAlgorithm().forward();
              }
            }
          }
        };
    timer.start();
  }

  @Override
  public void pause() {}

  @Override
  public void reset() {}
}
