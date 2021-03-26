package tech.houssemnasri.impl.toolbox;

import tech.houssemnasri.api.toolbox.IToolbox;
import tech.houssemnasri.api.toolbox.IToolboxPresenter;
import tech.houssemnasri.api.toolbox.IToolboxView;

public class ToolboxPresenter implements IToolboxPresenter {
  private IToolboxView toolboxView;
  private IToolbox toolbox;

  public ToolboxPresenter(IToolbox toolbox, IToolboxView toolboxView) {
    setToolbox(toolbox);
    setView(toolboxView);
  }

  @Override
  public void setView(IToolboxView view) {
    if (view == null) return;
    ;
    this.toolboxView = view;
    this.toolboxView.setPresenter(this);
  }

  @Override
  public void setToolbox(IToolbox toolbox) {
    this.toolbox = toolbox;
  }
}
