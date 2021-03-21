package tech.houssemnasri.impl.command;

import tech.houssemnasri.api.command.AlgorithmCommand;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.pathfinder.BaseAlgorithm;

public class SetCurrentNodeCommand extends AlgorithmCommand {
  private final INode prevCurrentNode;

  public SetCurrentNodeCommand(BaseAlgorithm algorithm, INode node) {
    super(algorithm, node);
    this.prevCurrentNode = algorithm.getCurrentNode();
  }

  @Override
  public void execute() {
    algorithm.setCurrentNode(node);
  }

  @Override
  public void undo() {
    algorithm.setCurrentNode(prevCurrentNode);
  }
}
