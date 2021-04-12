package tech.houssemnasri.api.command;

import tech.houssemnasri.api.pathfinder.BaseAlgorithm;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.pathfinder.AlgorithmStep;

/**
 * This class {@code AlgoCommand} responsible for generalizing common actions performed by
 * pathfinding algorithms like opening a node or closing it.
 */
public abstract class AlgorithmCommand implements ICommand {
  private final AlgorithmCommandContext commandContext;

  public AlgorithmCommand(AlgorithmCommandContext commandContext) {
    this.commandContext = commandContext;
  }

  public AlgorithmCommand(BaseAlgorithm algorithm, AlgorithmStep step, INode node) {
    this(AlgorithmCommandContext.create(algorithm, step, node));
  }

  protected abstract void justExecute();

  @Override
  public final void execute() {
    justExecute();
    commandContext.getStep().push(this);
  }

  public AlgorithmCommandContext getCommandContext() {
    return commandContext;
  }

  public BaseAlgorithm getAlgorithm() {
    return commandContext.getAlgorithm();
  }

  public AlgorithmStep getStep() {
    return commandContext.getStep();
  }

  public INode getNode() {
    return commandContext.getNode();
  }
}
