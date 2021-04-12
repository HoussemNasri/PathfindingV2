package tech.houssemnasri.impl.command;

import tech.houssemnasri.api.command.AlgorithmCommand;
import tech.houssemnasri.api.command.AlgorithmCommandContext;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.pathfinder.BaseAlgorithm;
import tech.houssemnasri.impl.pathfinder.AlgorithmStep;

public class SetCurrentNodeCommand extends AlgorithmCommand {
  private final INode prevCurrentNode;

  public SetCurrentNodeCommand(AlgorithmCommandContext commandContext) {
    super(commandContext);
    this.prevCurrentNode = getAlgorithm().getCurrentNode();
  }

  public SetCurrentNodeCommand(BaseAlgorithm algorithm, AlgorithmStep step, INode node) {
    this(AlgorithmCommandContext.create(algorithm, step, node));
  }

  @Override
  protected void justExecute() {
    getAlgorithm().setCurrentNode(getNode());
  }

  @Override
  public void undo() {
    getAlgorithm().setCurrentNode(prevCurrentNode);
  }
}
