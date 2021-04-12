package tech.houssemnasri.impl.command;

import tech.houssemnasri.api.command.AlgorithmCommandContext;
import tech.houssemnasri.api.pathfinder.BaseAlgorithm;
import tech.houssemnasri.api.command.AlgorithmCommand;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.pathfinder.AlgorithmStep;

import static tech.houssemnasri.api.node.INode.*;

public class CloseNodeCommand extends AlgorithmCommand {
  private final Type undoType;

  public CloseNodeCommand(AlgorithmCommandContext commandContext) {
    super(commandContext);
    this.undoType = getNode().getType();
  }

  public CloseNodeCommand(BaseAlgorithm algorithm, AlgorithmStep step, INode node) {
    this(AlgorithmCommandContext.create(algorithm, step, node));
  }

  @Override
  protected void justExecute() {
    if (isNodeClosed()) {
      return;
    }
    getAlgorithm().getOpenSet().remove(getNode());
    getAlgorithm().getClosedSet().add(getNode());
    getNode().setType(Type.CLOSED);
  }

  private boolean isNodeClosed() {
    return getAlgorithm().getClosedSet().contains(getNode());
  }

  @Override
  public void undo() {
    getAlgorithm().getOpenSet().add(getNode());
    getAlgorithm().getClosedSet().remove(getNode());
    getNode().setType(undoType);
  }
}
