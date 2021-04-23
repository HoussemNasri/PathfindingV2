package tech.houssemnasri.command;

import tech.houssemnasri.command.AlgorithmCommandContext;
import tech.houssemnasri.pathfinder.BaseAlgorithm;
import tech.houssemnasri.command.AlgorithmCommand;
import tech.houssemnasri.node.INode;
import tech.houssemnasri.pathfinder.AlgorithmStep;

import static tech.houssemnasri.node.INode.*;

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
    getAlgorithm().getUnvisitedNodes().remove(getNode());
    getAlgorithm().getVisitedNodes().add(getNode());
    getNode().setType(Type.CLOSED);
  }

  private boolean isNodeClosed() {
    return getAlgorithm().getVisitedNodes().contains(getNode());
  }

  @Override
  public void undo() {
    getAlgorithm().getUnvisitedNodes().add(getNode());
    getAlgorithm().getVisitedNodes().remove(getNode());
    getNode().setType(undoType);
  }
}
