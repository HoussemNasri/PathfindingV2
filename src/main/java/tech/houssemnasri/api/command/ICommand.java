package tech.houssemnasri.api.command;

public interface ICommand {
  void execute();
  /** Execute command and return {@code this} object */
  default ICommand executeAndReturn() {
    execute();
    return this;
  }

  void undo();
}
