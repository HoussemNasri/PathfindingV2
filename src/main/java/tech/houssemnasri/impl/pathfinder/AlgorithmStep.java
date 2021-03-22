package tech.houssemnasri.impl.pathfinder;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import tech.houssemnasri.api.command.ICommand;

public class AlgorithmStep {
  protected Stack<ICommand> commands = new Stack<>();

  public AlgorithmStep(List<ICommand> commands) {
    this.commands.addAll(commands);
  }

  public AlgorithmStep(ICommand... commands) {
    this(Arrays.asList(commands));
  }

  public AlgorithmStep() {
    this(List.of());
  }

  public void push(ICommand command) {
    commands.push(command);
  }

  public ICommand pop() {
    return commands.pop();
  }

  public boolean isEmpty() {
    return commands.isEmpty();
  }
}
