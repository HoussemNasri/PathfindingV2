package tech.houssemnasri.pathfinder.dfs;

import java.util.Stack;

import tech.houssemnasri.command.AlgorithmCommand;
import tech.houssemnasri.command.AlgorithmCommandContext;
import tech.houssemnasri.node.INode;
import tech.houssemnasri.pathfinder.AlgorithmStep;
import tech.houssemnasri.pathfinder.BaseAlgorithm;

public class PushNodeCommand extends AlgorithmCommand {
  private final Stack<INode> nodeStack;

  public PushNodeCommand(AlgorithmCommandContext commandContext, final Stack<INode> nodeStack) {
    super(commandContext);
    this.nodeStack = nodeStack;
  }

  public PushNodeCommand(
      BaseAlgorithm algorithm, AlgorithmStep step, INode node, Stack<INode> nodeStack) {
    this(AlgorithmCommandContext.create(algorithm, step, node), nodeStack);
  }

  @Override
  protected void justExecute() {
    if (!nodeStack.contains(getNode())) {
      nodeStack.push(getNode());
    }
  }

  @Override
  public void undo() {
    nodeStack.remove(getNode());
  }
}
