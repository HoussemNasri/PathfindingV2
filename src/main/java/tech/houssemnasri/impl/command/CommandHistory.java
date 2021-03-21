package tech.houssemnasri.impl.command;

import java.util.List;
import java.util.Stack;

public class CommandHistory {
  protected Stack<CommandRecord> commandRecords = new Stack<>();

  public CommandHistory(List<CommandRecord> commandRecords) {
    this.commandRecords.addAll(commandRecords);
  }

  public CommandHistory() {
    this(List.of());
  }

  public void push(CommandRecord commandRecord) {
    commandRecords.push(commandRecord);
  }

  public CommandRecord pop() {
    return commandRecords.pop();
  }

  public boolean isEmpty() {
    return commandRecords.isEmpty();
  }
}
