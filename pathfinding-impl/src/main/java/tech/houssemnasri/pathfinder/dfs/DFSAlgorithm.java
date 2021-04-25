package tech.houssemnasri.pathfinder.dfs;

import java.util.List;
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
      if (isVisited(grid.getSourceNode())) {
        step.markAsFinal();
        return step;
      } else {
        new PushNodeCommand(this, step, grid.getSourceNode(), nodeStack).execute();
      }
    }
    new SetCurrentNodeCommand(this, step, popNode(step)).execute();
    if (getGrid().isDestinationNode(getCurrentNode())) {
      step.markAsFinal();
      return step;
    }
    if (!isVisited(getCurrentNode())) {
      markAsVisited(step, getCurrentNode());
      List<INode> neighbors = getCurrentNodeNeighbors();
      for (INode neighbor : neighbors) {
        if (not(isVisited(neighbor))) {
          new PushNodeCommand(this, step, neighbor, nodeStack).execute();
          new SetParentCommand(this, step, neighbor, getCurrentNode()).execute();
        }
      }
    }
    return step;
  }

  private INode popNode(AlgorithmStep step) {
    return new PopNodeCommand(step, nodeStack).pop();
  }

  private void markAsVisited(AlgorithmStep step, INode node) {

    // new OpenNodeCommand(context).execute();
    new CloseNodeCommand(this, step, node).execute();
  }

  @Override
  public void reset() {
    super.reset();
    nodeStack.clear();
  }
}
