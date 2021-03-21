package tech.houssemnasri.impl.command;

import tech.houssemnasri.api.grid.IGrid;
import tech.houssemnasri.api.pathfinder.BaseAlgorithm;
import tech.houssemnasri.api.command.AlgorithmCommand;
import tech.houssemnasri.api.node.INode;
import static tech.houssemnasri.api.node.INode.*;

public class CloseNodeCommand extends AlgorithmCommand {
  public CloseNodeCommand(BaseAlgorithm algorithm, INode node) {
    super(algorithm, node);
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
    IGrid grid = getAlgorithm().getGrid();
    if (grid.isSourceNode(node)) {
      node.setType(Type.SOURCE);
    } else if (grid.isDestinationNode(node)) {
      System.out.println("Destination");
      node.setType(Type.DESTINATION);
    } else {
      node.setType(Type.OPEN);
    }
  }
}
