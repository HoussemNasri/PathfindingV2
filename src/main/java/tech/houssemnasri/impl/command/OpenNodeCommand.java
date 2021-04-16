package tech.houssemnasri.impl.command;

import tech.houssemnasri.api.command.AlgorithmCommand;
import tech.houssemnasri.api.command.AlgorithmCommandContext;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.pathfinder.BaseAlgorithm;
import tech.houssemnasri.api.pathfinder.AlgorithmStep;

import static tech.houssemnasri.api.node.INode.Type;

public class OpenNodeCommand extends AlgorithmCommand {
  private final Type undoType;

  public OpenNodeCommand(AlgorithmCommandContext commandContext) {
    super(commandContext);
    this.undoType = getNode().getType();
  }

  public OpenNodeCommand(BaseAlgorithm algorithm, AlgorithmStep step, INode node) {
    this(AlgorithmCommandContext.create(algorithm, step, node));
  }

  @Override
  protected void justExecute() {
    if (isNodeOpen()) {
      return;
    }
    getAlgorithm().getUnvisitedNodes().add(getNode());
    getAlgorithm().getVisitedNodes().remove(getNode());
    getNode().setType(Type.OPEN);
  }

  private boolean isNodeOpen() {
    return getAlgorithm().getUnvisitedNodes().contains(getNode());
  }

  @Override
  public void undo() {
    getAlgorithm().getUnvisitedNodes().remove(getNode());
    getNode().setType(undoType);
  }
}
