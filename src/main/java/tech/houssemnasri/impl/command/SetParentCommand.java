package tech.houssemnasri.impl.command;

import tech.houssemnasri.api.command.AlgorithmCommand;
import tech.houssemnasri.api.command.AlgorithmCommandContext;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.pathfinder.BaseAlgorithm;
import tech.houssemnasri.api.pathfinder.AlgorithmStep;

public class SetParentCommand extends AlgorithmCommand {
  private final INode prevParent;
  private final INode parent;

  public SetParentCommand(AlgorithmCommandContext commandContext, INode parent) {
    super(commandContext);
    this.prevParent = getNode().getParent();
    this.parent = parent;
  }

  public SetParentCommand(BaseAlgorithm algorithm, AlgorithmStep step, INode node, INode parent) {
    this(AlgorithmCommandContext.create(algorithm, step, node), parent);
  }

  @Override
  protected void justExecute() {
    getNode().setParent(parent);
  }

  @Override
  public void undo() {
    getNode().setParent(prevParent);
  }
}
