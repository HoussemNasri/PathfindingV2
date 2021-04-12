package tech.houssemnasri.impl.pathfinder;

import java.util.List;
import java.util.Stack;

public class AlgorithmHistory {
  protected Stack<AlgorithmStep> stepStack = new Stack<>();

  public AlgorithmHistory(List<AlgorithmStep> steps) {
    stepStack.addAll(steps);
  }

  public AlgorithmHistory() {
    this(List.of());
  }

  public void push(AlgorithmStep steps) {
    stepStack.push(steps);
  }

  public AlgorithmStep pop() {
    return stepStack.pop();
  }

  public AlgorithmStep peek() {
    return stepStack.peek();
  }

  public boolean isEmpty() {
    return stepStack.isEmpty();
  }

  public void clear() {
    stepStack.clear();
  }
}
