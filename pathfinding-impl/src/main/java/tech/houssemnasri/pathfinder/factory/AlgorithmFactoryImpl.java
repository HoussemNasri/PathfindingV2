package tech.houssemnasri.pathfinder.factory;

import tech.houssemnasri.AlgorithmDescriptor;
import tech.houssemnasri.grid.IGrid;
import tech.houssemnasri.pathfinder.BaseAlgorithm;
import tech.houssemnasri.pathfinder.astar.AStarAlgorithm;
import tech.houssemnasri.pathfinder.dfs.DFSAlgorithm;
import tech.houssemnasri.pathfinder.dijkstra.DijkstraAlgorithm;

public class AlgorithmFactoryImpl implements AlgorithmFactory{
  private final IGrid grid;

  public AlgorithmFactoryImpl(IGrid grid) {
    this.grid = grid;
  }

  @Override
  public BaseAlgorithm getAlgorithm(AlgorithmDescriptor descriptor) {
      return switch (descriptor){
          case A_STAR -> new AStarAlgorithm(grid);
          case DIJKSTRA -> new DijkstraAlgorithm(grid);
          case DFS -> new DFSAlgorithm(grid);
      };
  }
}
