package tech.houssemnasri.impl.command;

import tech.houssemnasri.api.pathfinder.BaseAlgorithm;
import tech.houssemnasri.api.command.AlgorithmCommand;
import tech.houssemnasri.api.node.INode;
import static tech.houssemnasri.api.node.INode.*;

public class CloseNodeCommand extends AlgorithmCommand {
  private final Type undoType;

  public CloseNodeCommand(BaseAlgorithm algorithm, INode node) {
    super(algorithm, node);
    this.undoType = node.getType();
  }

  @Override
  public void execute() {
    if (isNodeClosed()) {
      return;
    }
    algorithm.getOpenSet().remove(node);
    algorithm.getClosedSet().add(node);
    node.setType(Type.CLOSED);
  }

  private boolean isNodeClosed() {
    return algorithm.getClosedSet().contains(node);
  }

  @Override
  public void undo() {
    algorithm.getOpenSet().add(node);
    algorithm.getClosedSet().remove(node);
    node.setType(undoType);
  }
}
