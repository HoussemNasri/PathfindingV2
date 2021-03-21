package tech.houssemnasri.impl.command;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import tech.houssemnasri.api.command.ICommand;

public class CommandRecord {
  protected Stack<ICommand> commands = new Stack<>();

  public CommandRecord(List<ICommand> commands) {
    this.commands.addAll(commands);
  }

  public CommandRecord(ICommand... commands) {
    this(Arrays.asList(commands));
  }

  public CommandRecord() {
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
