package tech.houssemnasri.impl.command;

import java.util.LinkedList;
import java.util.Queue;

import tech.houssemnasri.api.command.ICommand;
import tech.houssemnasri.api.node.INode;

public class TracePathCommand implements ICommand {
  private final Queue<INode.Type> typeCache = new LinkedList<>();
  private final INode destinationNode;

  public TracePathCommand(INode destinationNode) {
    this.destinationNode = destinationNode;
  }

  @Override
  public void execute() {
    INode tempNode = destinationNode;
    while (tempNode != null) {
      typeCache.offer(tempNode.getType());
      tempNode.setType(INode.Type.PATH);
      tempNode = tempNode.getParent();
    }
  }

  @Override
  public void undo() {
    INode tempNode = destinationNode;
    while (tempNode != null) {
      tempNode.setType(typeCache.poll());
      tempNode = tempNode.getParent();
    }
  }
}
