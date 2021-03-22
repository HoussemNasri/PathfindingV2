package tech.houssemnasri.impl.command;

import java.util.LinkedList;
import java.util.Queue;

import tech.houssemnasri.api.command.AlgorithmCommand;
import tech.houssemnasri.api.node.INode;
import tech.houssemnasri.api.pathfinder.BaseAlgorithm;

public class TracePathCommand extends AlgorithmCommand {
  private final Queue<INode.Type> typeCache = new LinkedList<>();

  public TracePathCommand(BaseAlgorithm algorithm, INode destinationNode) {
    super(algorithm, destinationNode);
  }

  @Override
  public void execute() {
    INode tempNode = getNode();
    while (tempNode != null) {
      typeCache.offer(tempNode.getType());
      tempNode.setType(INode.Type.PATH);
      tempNode = tempNode.getParent();
    }
  }

  @Override
  public void undo() {
    INode tempNode = getNode();
    while (tempNode != null) {
      tempNode.setType(typeCache.poll());
      tempNode = tempNode.getParent();
    }
  }
}
