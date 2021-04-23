package tech.houssemnasri.command;

import tech.houssemnasri.command.AlgorithmCommand;
import tech.houssemnasri.command.AlgorithmCommandContext;
import tech.houssemnasri.node.INode;
import tech.houssemnasri.pathfinder.BaseAlgorithm;
import tech.houssemnasri.pathfinder.AlgorithmStep;

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
