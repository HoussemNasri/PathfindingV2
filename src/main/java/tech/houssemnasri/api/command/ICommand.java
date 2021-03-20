package tech.houssemnasri.api.command;

public interface ICommand {
  void execute();

  void undo();
}
