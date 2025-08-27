package tech.houssemnasri.pathfinder.bfs;

import java.util.Queue;

import tech.houssemnasri.command.AlgorithmCommand;
import tech.houssemnasri.command.AlgorithmCommandContext;
import tech.houssemnasri.node.INode;
import tech.houssemnasri.pathfinder.AlgorithmStep;
import tech.houssemnasri.pathfinder.BaseAlgorithm;

/**
 * Command to add a node to the BFS queue.
 * This command supports undo operations for step-by-step algorithm visualization.
 */
public class EnqueueNodeCommand extends AlgorithmCommand {
  private final Queue<INode> nodeQueue;

  public EnqueueNodeCommand(AlgorithmCommandContext commandContext, final Queue<INode> nodeQueue) {
    super(commandContext);
    this.nodeQueue = nodeQueue;
  }

  public EnqueueNodeCommand(
      BaseAlgorithm algorithm, AlgorithmStep step, INode node, Queue<INode> nodeQueue) {
    this(AlgorithmCommandContext.create(algorithm, step, node), nodeQueue);
  }

  @Override
  protected void justExecute() {
    nodeQueue.offer(getNode());
  }

  @Override
  public void undo() {
    nodeQueue.remove(getNode());
  }
}