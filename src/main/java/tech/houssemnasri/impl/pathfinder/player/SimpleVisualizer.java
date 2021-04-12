package tech.houssemnasri.impl.pathfinder.player;

import tech.houssemnasri.BooleanExtensions;
import tech.houssemnasri.api.pathfinder.BaseAlgorithm;
import tech.houssemnasri.api.pathfinder.Visualizer;

public class SimpleVisualizer extends Visualizer implements BooleanExtensions {
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
  public void handle(long now) {
    if (start == -1 || now - start >= getDelay()) {
      start = now;
      if (getAlgorithm().isPathFound()) {
        finishVisualization();
      } else {
        forward();
      }
    }
  }
}
