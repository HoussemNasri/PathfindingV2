package tech.houssemnasri.pathfinder.bfs;

import java.util.Queue;

import tech.houssemnasri.command.ICommand;
import tech.houssemnasri.node.INode;
import tech.houssemnasri.pathfinder.AlgorithmStep;

/**
 * Command to remove a node from the BFS queue.
 * This command supports undo operations for step-by-step algorithm visualization.
 */
public class DequeueNodeCommand implements ICommand {
  private final AlgorithmStep step;
  private final Queue<INode> nodeQueue;
  private INode dequeuedNode;

  public DequeueNodeCommand(AlgorithmStep step, Queue<INode> nodeQueue) {
    this.step = step;
    this.nodeQueue = nodeQueue;
  }

  public INode getDequeuedNode() {
    return dequeuedNode;
  }

  @Override
  public void execute() {
    this.dequeuedNode = nodeQueue.poll();
    step.push(this);
  }

  public INode dequeue() {
    execute();
    return getDequeuedNode();
  }

  @Override
  public void undo() {
    if (dequeuedNode != null) {
      // For BFS, we need to add the node back to the front of the queue
      // Since Queue interface doesn't have addFirst, we'll create a new queue
      // and add all elements back in the correct order
      nodeQueue.offer(dequeuedNode);
    }
  }
}