package tech.houssemnasri.command;

public interface ICommand {
  void execute();

  void undo();
}
