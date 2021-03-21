package tech.houssemnasri.impl.command;

import tech.houssemnasri.api.command.AlgorithmCommand;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.pathfinder.BaseAlgorithm;

public class SetParentCommand extends AlgorithmCommand {
  private final INode prevParent;
  private final INode parent;

  public SetParentCommand(BaseAlgorithm algorithm, INode node, INode parent) {
    super(algorithm, node);
    this.prevParent = node.getParent();
    this.parent = parent;
  }

  @Override
  public void execute() {
    getNode().setParent(parent);
  }

  @Override
  public void undo() {
    getNode().setParent(prevParent);
  }
}
