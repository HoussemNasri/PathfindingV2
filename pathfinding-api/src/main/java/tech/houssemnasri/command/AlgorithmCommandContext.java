package tech.houssemnasri.command;

import tech.houssemnasri.node.INode;
import tech.houssemnasri.pathfinder.BaseAlgorithm;
import tech.houssemnasri.pathfinder.AlgorithmStep;

public class AlgorithmCommandContext {
  private final BaseAlgorithm algorithm;
  private final AlgorithmStep step;
  private final INode node;

  public AlgorithmCommandContext(BaseAlgorithm algorithm, AlgorithmStep step, INode node) {
    this.algorithm = algorithm;
    this.step = step;
    this.node = node;
  }

  public BaseAlgorithm getAlgorithm() {
    return algorithm;
  }

  public AlgorithmStep getStep() {
    return step;
  }

  public INode getNode() {
    return node;
  }

  public static AlgorithmCommandContext create(
      BaseAlgorithm algorithm, AlgorithmStep step, INode node) {
    return new AlgorithmCommandContext(algorithm, step, node);
  }
}
