package tech.houssemnasri.pathfinder.visualizer;

import tech.houssemnasri.BooleanExtensions;
import tech.houssemnasri.pathfinder.BaseAlgorithm;

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
      getAlgorithm().tracePath();
      System.out.println("Dude! We found the path.");
    }
  }

  @Override
  public void back() {
    if (not(getAlgorithm().getVisitedNodes().isEmpty())) {
      getAlgorithm().back();
      if (getAlgorithm().getVisitedNodes().isEmpty()) {
        // We do this to unlock the start and destination nodes to be able to drag them.
        reset();
      }
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
