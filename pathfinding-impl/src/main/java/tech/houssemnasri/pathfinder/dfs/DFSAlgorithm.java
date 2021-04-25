package tech.houssemnasri.pathfinder.dfs;

import java.util.Stack;

import tech.houssemnasri.command.CloseNodeCommand;
import tech.houssemnasri.command.OpenNodeCommand;
import tech.houssemnasri.command.SetCurrentNodeCommand;
import tech.houssemnasri.command.SetParentCommand;
import tech.houssemnasri.grid.IGrid;
import tech.houssemnasri.node.INode;
import tech.houssemnasri.pathfinder.AlgorithmStep;
import tech.houssemnasri.pathfinder.BaseAlgorithm;

public class DFSAlgorithm extends BaseAlgorithm {
  private final Stack<INode> nodeStack = new Stack<>();

  public DFSAlgorithm(IGrid grid, boolean isDiagonalAllowed) {
    super(grid, isDiagonalAllowed);
  }

  public DFSAlgorithm(IGrid grid) {
    super(grid);
  }

  @Override
  protected AlgorithmStep advance() {
    AlgorithmStep step = new AlgorithmStep();
    if (nodeStack.isEmpty()) {
      if (not(isVisited(grid.getSourceNode()))) {
        new PushNodeCommand(this, step, grid.getSourceNode(), nodeStack).execute();
      } else {
        // We are stuck!
        return finalize(step);
      }
    }
    INode currentNode = new PopNodeCommand(step, nodeStack).pop();
    new SetCurrentNodeCommand(this, step, currentNode).execute();
    if (getGrid().isDestinationNode(getCurrentNode())) {
      return finalize(step);
    }
    if (not(isVisited(getCurrentNode()))) {
      visitNode(getCurrentNode(), step);
      for (INode neighbor : getCurrentNodeNeighbors()) {
        if (not(isVisited(neighbor))) {
          new PushNodeCommand(this, step, neighbor, nodeStack).execute();
          new SetParentCommand(this, step, neighbor, getCurrentNode()).execute();
          new OpenNodeCommand(this, step, neighbor).execute();
        }
      }
      return step;
    }
    // If currentNode is visited, the algorithm won't do anything therefore the visualization will
    // look frozen, we recursively call the advance method to try and find a none visited node.
    return advance();
  }

  private void visitNode(INode node, AlgorithmStep step) {
    new OpenNodeCommand(this, step, node).execute();
    new CloseNodeCommand(this, step, node).execute();
  }

  @Override
  public void reset() {
    super.reset();
    nodeStack.clear();
  }
}
