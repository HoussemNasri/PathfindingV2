package tech.houssemnasri.pathfinder.dfs;

import java.util.Stack;

import tech.houssemnasri.command.ICommand;
import tech.houssemnasri.node.INode;
import tech.houssemnasri.pathfinder.AlgorithmStep;

public class PopNodeCommand implements ICommand {
  private final AlgorithmStep step;
  private final Stack<INode> nodeStack;
  private INode popNode;

  public PopNodeCommand(AlgorithmStep step, Stack<INode> nodeStack) {
    this.step = step;
    this.nodeStack = nodeStack;
  }

  public INode getPoppedNode() {
    return popNode;
  }

  @Override
  public void execute() {
    this.popNode = nodeStack.pop();
    step.push(this);
  }

  public INode pop() {
    execute();
    return getPoppedNode();
  }

  @Override
  public void undo() {
    nodeStack.push(popNode);
  }
}
