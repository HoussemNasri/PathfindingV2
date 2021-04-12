package tech.houssemnasri.impl.pathfinder.player;

import tech.houssemnasri.BooleanExtensions;
import tech.houssemnasri.api.pathfinder.BaseAlgorithm;
import tech.houssemnasri.api.pathfinder.Visualizer;

public class SimpleVisualizer extends Visualizer implements BooleanExtensions {
  private static final long SPEED_SLOW = 700000000;
  private static final long SPEED_MEDIUM = 70000000;
  private static final long SPEED_FAST = 9000000;
  private long start = -1;

  public SimpleVisualizer(BaseAlgorithm algorithm) {
    super(algorithm);
  }

  @Override
  public void forward() {
    if (not(getAlgorithm().isPathFound())) {
      getAlgorithm().forward();
    } else {
      System.out.println("Dude! We found the path.");
    }
  }

  @Override
  public void back() {
    if (not(getAlgorithm().getOpenSet().isEmpty())) {
      getAlgorithm().back();
    } else {
      System.out.println("Stop! Motherfucker.");
    }
  }

  @Override
  public void visualize() {
    if (getAlgorithm().isPathFound()) {
      getAlgorithm().reset();
    }
    super.visualize();
  }

  @Override
  public void pause() {
    super.pause();
  }

  @Override
  public void reset() {
    super.reset();
    getAlgorithm().reset();
  }

  @Override
  public void handle(long now) {
    if (start == -1 || now - start >= SPEED_FAST) {
      start = now;
      if (getAlgorithm().isPathFound()) {
        super.reset();
        notifyAllOnFinishedListeners();
      } else {
        forward();
      }
    }
  }
}
