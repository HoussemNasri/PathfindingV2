package tech.houssemnasri.command;

import tech.houssemnasri.command.AlgorithmCommand;
import tech.houssemnasri.command.AlgorithmCommandContext;
import tech.houssemnasri.node.INode;
import tech.houssemnasri.pathfinder.BaseAlgorithm;
import tech.houssemnasri.pathfinder.AlgorithmStep;

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
