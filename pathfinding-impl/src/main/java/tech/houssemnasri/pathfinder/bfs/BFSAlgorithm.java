package tech.houssemnasri.pathfinder.bfs;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import tech.houssemnasri.grid.IGrid;
import tech.houssemnasri.node.INode;
import tech.houssemnasri.pathfinder.AlgorithmStep;
import tech.houssemnasri.pathfinder.BaseAlgorithm;
import tech.houssemnasri.command.CloseNodeCommand;
import tech.houssemnasri.command.OpenNodeCommand;
import tech.houssemnasri.command.SetCurrentNodeCommand;
import tech.houssemnasri.command.SetParentCommand;

/**
 * Breadth-First Search (BFS) pathfinding algorithm implementation.
 * 
 * BFS explores nodes level by level, guaranteeing the shortest path in unweighted graphs.
 * It uses a queue to maintain the order of exploration, visiting all neighbors of the current
 * level before moving to the next level.
 */
public class BFSAlgorithm extends BaseAlgorithm {
  private final Queue<INode> nodeQueue = new LinkedList<>();

  public BFSAlgorithm(IGrid grid, boolean isDiagonalAllowed) {
    super(grid, isDiagonalAllowed);
  }

  public BFSAlgorithm(IGrid grid) {
    super(grid, false);
  }

  @Override
  protected AlgorithmStep advance() {
    AlgorithmStep step = new AlgorithmStep();
    
    // Initialize with source node if queue is empty
    if (nodeQueue.isEmpty()) {
      if (not(isVisited(grid.getSourceNode()))) {
        new EnqueueNodeCommand(this, step, grid.getSourceNode(), nodeQueue).execute();
      } else {
        // We are stuck!
        return finalize(step);
      }
    }
    
    // Get the next node from the queue (FIFO - breadth-first)
    INode currentNode = new DequeueNodeCommand(step, nodeQueue).dequeue();
    new SetCurrentNodeCommand(this, step, currentNode).execute();
    
    // Check if we've reached the destination
    if (getGrid().isDestinationNode(getCurrentNode())) {
      return finalize(step);
    }
    
    // Only process if not already visited
    if (not(isVisited(getCurrentNode()))) {
      visitNode(getCurrentNode(), step);
      
      // Explore all neighbors of the current node
      List<INode> neighbors = getCurrentNodeNeighbors();
      for (INode neighbor : neighbors) {
        if (not(isVisited(neighbor)) && not(isInQueue(neighbor))) {
          new EnqueueNodeCommand(this, step, neighbor, nodeQueue).execute();
          new SetParentCommand(this, step, neighbor, getCurrentNode()).execute();
          new OpenNodeCommand(this, step, neighbor).execute();
        }
      }
      return step;
    }
    
    // If currentNode is visited, recursively call advance to find an unvisited node
    return advance();
  }

  private void visitNode(INode node, AlgorithmStep step) {
    new OpenNodeCommand(this, step, node).execute();
    new CloseNodeCommand(this, step, node).execute();
  }

  private boolean isInQueue(INode node) {
    return nodeQueue.contains(node);
  }

  @Override
  public void reset() {
    super.reset();
    nodeQueue.clear();
  }
}