package tech.houssemnasri.api.pathfinder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import tech.houssemnasri.api.command.ICommand;

public class AlgorithmStep {
  protected Stack<ICommand> commandStack = new Stack<>();
  private boolean isFinal;

  public AlgorithmStep(List<ICommand> allCommands) {
    this.commandStack.addAll(allCommands);
  }

  public AlgorithmStep(ICommand... allCommands) {
    this(Arrays.asList(allCommands));
  }

  public AlgorithmStep() {
    this(Collections.emptyList());
  }

  public void push(ICommand command) {
    commandStack.push(command);
  }

  /** Execute the command and save it. */
  public void pushAndExecute(ICommand command) {
    push(command.executeAndReturn());
  }

  /** Undo all commands belonging to {@code this} step. */
  public void cancel() {
    while (!isEmpty()) {
      commandStack.pop().undo();
    }
  }

  public boolean isEmpty() {
    return commandStack.isEmpty();
  }

  public boolean isFinalStep() {
    return isFinal;
  }

  public void markAsFinal() {
    this.isFinal = true;
  }

  public ICommand peek() {
    return commandStack.peek();
  }
}
