package tech.houssemnasri.impl.pathfinder;

import java.util.List;
import java.util.Stack;

import tech.houssemnasri.impl.command.CommandRecord;

public class AlgorithmHistory {
  protected Stack<CommandRecord> commandRecords = new Stack<>();

  public AlgorithmHistory(List<CommandRecord> commandRecords) {
    this.commandRecords.addAll(commandRecords);
  }

  public AlgorithmHistory() {
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
