package tech.houssemnasri.impl.pathfinder.factory;

import tech.houssemnasri.api.grid.IGrid;
import tech.houssemnasri.api.pathfinder.BaseAlgorithm;
import tech.houssemnasri.impl.AlgorithmDescriptor;
import tech.houssemnasri.impl.pathfinder.astar.AStarAlgorithm;
import tech.houssemnasri.impl.pathfinder.dijkstra.DijkstraAlgorithm;

public class AlgorithmFactory {
  private final IGrid grid;

  public AlgorithmFactory(IGrid grid) {
    this.grid = grid;
  }

  public BaseAlgorithm getAlgorithm(AlgorithmDescriptor descriptor) {
      return switch (descriptor){
          case A_STAR -> new AStarAlgorithm(grid);
          case DIJKSTRA -> new DijkstraAlgorithm(grid);
      };
  }
}
