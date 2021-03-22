package tech.houssemnasri.impl.pathfinder;

import java.util.List;
import java.util.Stack;

public class AlgorithmHistory {
  protected Stack<AlgorithmStep> algorithmSteps = new Stack<>();

  public AlgorithmHistory(List<AlgorithmStep> algorithmSteps) {
    this.algorithmSteps.addAll(algorithmSteps);
  }

  public AlgorithmHistory() {
    this(List.of());
  }

  public void push(AlgorithmStep algorithmStep) {
    algorithmSteps.push(algorithmStep);
  }

  public AlgorithmStep pop() {
    return algorithmSteps.pop();
  }

  public boolean isEmpty() {
    return algorithmSteps.isEmpty();
  }

  public void clear() {
    algorithmSteps.clear();
  }
}
